package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.util.SnowFlake;
import cn.qkmango.ccms.domain.bind.CardState;
import cn.qkmango.ccms.domain.bind.ExceptionInfoState;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel1;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel2;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel3;
import cn.qkmango.ccms.domain.bind.trade.TradeState;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.entity.ExceptionInfo;
import cn.qkmango.ccms.domain.entity.Trade;
import cn.qkmango.ccms.domain.vo.ResultPageParam;
import cn.qkmango.ccms.mvc.dao.CardDao;
import cn.qkmango.ccms.mvc.dao.ExceptionInfoDao;
import cn.qkmango.ccms.mvc.dao.TradeDao;
import cn.qkmango.ccms.mvc.service.AlipayService;
import cn.qkmango.ccms.pay.AlipayConfig;
import cn.qkmango.ccms.pay.AlipayTradeStatus;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-24 17:03
 */
@Service
public class AlipayServiceImpl implements AlipayService {

    @Resource(name = "alipayClient")
    private AlipayClient client;

    @Resource(name = "alipayConfig")
    private AlipayConfig config;

    @Resource
    private TradeDao tradeDao;

    @Resource
    private CardDao cardDao;

    @Resource
    private ExceptionInfoDao exceptionInfoDao;

    @Resource(name = "snowFlake")
    private SnowFlake sf;

    @Resource
    private TransactionTemplate tx;

    // 100
    private static final BigDecimal DIVIDE = new BigDecimal("100");

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 支付结果页面需要的数据
    private static final String PAY_RESULT_PAGE_PARAM = URLEncoder.encode(JSON.toJSONString(new ResultPageParam<>()
                    .setSuccess(true)
                    .setTitle("支付结果")
                    .setMessage("支付宝支付成功")
                    .setType("channel")
                    .setChannel("recharge"))
            , StandardCharsets.UTF_8);

    @Override
    public String pay(Integer account, Integer amount) throws AlipayApiException {
        // 先判断卡状态
        Card card = cardDao.getRecordByAccount(account);
        if (card.getState() != CardState.normal) {
            return null;
        }

        //交易ID
        long tradeId = sf.nextId();
        String subject = "一卡通充值";

        //添加交易
        boolean result = this.insertTrade(tradeId, account, subject, amount);
        if (!result) {
            return null;
        }

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        // 异步通知
        request.setNotifyUrl(config.notify);

        // 同步跳转页面
        request.setReturnUrl(config.redirect.url() + "?result=" + PAY_RESULT_PAGE_PARAM);

        //配置参数
        JSONObject bizContent = new JSONObject();
        bizContent.put("subject", subject);                                     // 订单标题
        bizContent.put("out_trade_no", tradeId);                                // 我们自己生成的订单编号
        bizContent.put("total_amount", new BigDecimal(amount).divide(DIVIDE));  // 订单的总金额
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");               // 固定配置
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);
        bizContent.put("time_expire", DATE_FORMAT.format(calendar.getTime()));            // 绝对超时时间

        // 执行请求，拿到响应的结果，返回给浏览器
        request.setBizContent(bizContent.toString());
        AlipayTradePagePayResponse response = client.pageExecute(request);
        if (response.isSuccess()) {
            // 调用SDK生成表单
            return response.getBody();
        }
        return null;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void payNotify(
            String alipayTradeNo,
            Long tradeId,
            String gmtPayment,
            String receiptAmount,
            AlipayTradeStatus alipayTradeStatus,
            String totalAmount,
            String sign,
            HttpServletRequest request) throws AlipayApiException {

        // 1. 判断是否支付成功
        // TRADE_SUCCESS 交易支付成功
        if (alipayTradeStatus != AlipayTradeStatus.TRADE_SUCCESS) {
            return;
        }

        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            params.put(name, request.getParameter(name));
        }

        // 2. 支付宝验签
        String content = AlipaySignature.getSignCheckContentV1(params);
        boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, config.alipayPublicKey, "UTF-8"); // 验证签名
        if (!checkSignature) {
            return;
        }

        // 获取 交易
        Trade trade = tradeDao.getRecordById(tradeId);
        if (trade == null) {
            insertExceptionInfo(tradeId, null, totalAmount);
            return;
        }
        Integer account = trade.getAccount();

        // 3. 支付成功，修改交易状态为 SUCCESS, 将金额添加到一卡通
        // 版本号一定是 0，如果不为0则说明已经被修改
        Integer version = 0;
        tx.execute(status -> {
            int affectedRows;
            // 回滚点
            Object savepoint = status.createSavepoint();

            // 修改交易状态
            affectedRows = tradeDao.updateState(tradeId, TradeState.success, version);
            if (affectedRows != 1) {
                // status.setRollbackOnly();
                status.rollbackToSavepoint(savepoint);
                insertExceptionInfo(tradeId, account, totalAmount);
                return null;
            }

            // 检查卡
            Card card = cardDao.getRecordByAccount(account);
            if (card == null || card.getState() != CardState.normal) {
                insertExceptionInfo(tradeId, account, totalAmount);
            }

            // 将金额添加到一卡通
            affectedRows = cardDao.addBalance(account, trade.getAmount(), card.getVersion());
            if (affectedRows != 1) {
                status.rollbackToSavepoint(savepoint);
                insertExceptionInfo(tradeId, account, totalAmount);
                return null;
            }

            return null;
        });
    }

    /**
     * 添加异常信息
     */
    private void insertExceptionInfo(Long tradeId, Integer account, String totalAmount) {
        String description = "交易ID [%s] 用户ID [%s]用户在支付宝成功支付一笔金额为 [%s] 的金额后系统未成功处理，导致用户实际支付了一笔金额但系统没有将其添加到账户(一卡通中)";
        ExceptionInfo info = new ExceptionInfo()
                .setRelationId(tradeId)
                .setDescription(String.format(description, tradeId, account, totalAmount))
                .setSolution(String.format("需要管理员手动对其账户(一卡通)进行充值金额为 %s", totalAmount))
                .setState(ExceptionInfoState.pending)
                .setVersion(0);
        exceptionInfoDao.insert(info);

    }


    /**
     * 插入交易
     *
     * @param amount 金额，单位分
     */
    private boolean insertTrade(long id, Integer account, String subject, int amount) {
        Trade trade = new Trade()
                .setId(id)
                .setAccount(account)
                .setLevel1(TradeLevel1.in)
                .setLevel2(TradeLevel2.alipay)
                .setLevel3(TradeLevel3.recharge)
                .setState(TradeState.processing)
                .setAmount(amount)
                .setCreator(account)
                .setCreateTime(System.currentTimeMillis())
                .setDescription(subject)
                .setVersion(0);

        Boolean result = tx.execute(status -> {
            int affectedRows = tradeDao.insert(trade);
            if (affectedRows != 1) {
                status.setRollbackOnly();
                return false;
            }
            return true;
        });

        return Boolean.TRUE.equals(result);
    }

}
