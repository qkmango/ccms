package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.util.SnowFlake;
import cn.qkmango.ccms.domain.bind.CardState;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel1;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel2;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel3;
import cn.qkmango.ccms.domain.bind.trade.TradeState;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.entity.Trade;
import cn.qkmango.ccms.domain.vo.ResultPageParam;
import cn.qkmango.ccms.middleware.mq.trade.pay.alipay.timeout.TradePayAlipayTimeoutMQSender;
import cn.qkmango.ccms.mvc.dao.CardDao;
import cn.qkmango.ccms.mvc.dao.TradeDao;
import cn.qkmango.ccms.mvc.service.AlipayService;
import cn.qkmango.ccms.pay.AlipayConfig;
import cn.qkmango.ccms.pay.AlipayNotify;
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
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
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

    @Resource(name = "snowFlake")
    private SnowFlake sf;
    @Resource
    private TradeDao tradeDao;
    @Resource
    private CardDao cardDao;
    @Resource
    private TransactionTemplate tx;
    @Resource
    private TradePayAlipayTimeoutMQSender mq;

    // 订单支付超时时间，单位 秒
    private static final int TRADE_TIMEOUT = 30 * 60;
    // 100
    private static final BigDecimal DIVIDE = new BigDecimal("100");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger logger = Logger.getLogger(AlipayServiceImpl.class);
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
        // 1. 先判断卡状态
        Card card = cardDao.getByAccount(account);
        if (card.getState() != CardState.normal) {
            return null;
        }

        // 交易ID
        long tradeId = sf.nextId();
        String subject = "一卡通充值";

        // 2. 向数据库中添加交易记录
        // 并发送延迟消息，让订单超时时关闭订单
        boolean result = this.insertAndSend(tradeId, account, subject, amount);
        if (!result) {
            return null;
        }

        // 3. 构建支付宝支付表单页面
        // 绝对超时时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, TRADE_TIMEOUT);
        String form = this.buildAlipayForm(tradeId, subject, amount, DATE_FORMAT.format(calendar.getTime()));

        // 5. 返回支付宝表单
        return form;
    }


    @Override
    public boolean notify(AlipayNotify notify, HttpServletRequest request) throws AlipayApiException {
        // 1. 判断是否支付成功
        // TRADE_SUCCESS(可以退款) 商家开通的产品支持退款功能的前提下，买家付款成功
        // 另外如果签约的产品支持退款，并且对应的产品默认支持能收到 TRADE_SUCCESS 或 TRADE_FINISHED 状态，
        //      该笔会先收到 TRADE_SUCCESS 交易状态，然后超过 交易有效退款时间 该笔交易会再次收到 TRADE_FINISHED 状态，
        //      实际该笔交易只支付了一次，切勿认为该笔交易支付两次
        if (notify.status != AlipayTradeStatus.TRADE_SUCCESS) {
            return false;
        }

        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            params.put(name, request.getParameter(name));
        }

        // 2. 支付宝验签
        String content = AlipaySignature.getSignCheckContentV1(params);
        boolean checkSignature = AlipaySignature.rsa256CheckContent(content, notify.sign, config.alipayPublicKey, "UTF-8"); // 验证签名
        if (!checkSignature) {
            return false;
        }

        // 获取 交易
        Trade trade = tradeDao.getById(notify.tradeId);
        if (trade == null) {
            // TODO 退款
            return false;
        }
        Integer account = trade.getAccount();

        // 3. 支付成功，修改交易状态为 SUCCESS, 将金额添加到一卡通
        // 版本号一定是 0，如果不为0则说明已经被修改
        Boolean result = tx.execute(status -> {
            int affectedRows;
            // 回滚点
            Object savepoint = status.createSavepoint();
            // 修改交易状态
            affectedRows = tradeDao.payed(notify.tradeId, notify.alipayTradeNo, 0);
            if (affectedRows != 1) {
                // TODO 退款
                status.rollbackToSavepoint(savepoint);
                return false;
            }

            // 检查卡
            Card card = cardDao.getByAccount(account);
            if (card == null || card.getState() != CardState.normal) {
                // TODO 退款
                return false;
            }

            // 将金额添加到一卡通
            affectedRows = cardDao.addBalance(account, trade.getAmount(), card.getVersion());
            if (affectedRows != 1) {
                status.rollbackToSavepoint(savepoint);
                // TODO 退款
                return false;
            }
            return true;
        });
        return Boolean.TRUE.equals(result);
    }

    /**
     * 将交易插入数据库，并发送延迟消息MQ
     */
    private boolean insertAndSend(long id, Integer account, String subject, int amount) {
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
            int affectedRows;
            // DB
            affectedRows = tradeDao.insert(trade);
            if (affectedRows != 1) {
                status.setRollbackOnly();
                return false;
            }

            // MQ
            boolean send = mq.syncSend(id);
            if (!send) {
                status.setRollbackOnly();
                return false;
            }

            return true;
        });

        return Boolean.TRUE.equals(result);
    }

    /**
     * 构建支付宝支付表单
     *
     * @param tradeId 交易ID
     * @param subject 交易主题
     * @param amount  金额，单位分
     * @param timeout 绝对超时时间
     */
    private String buildAlipayForm(Long tradeId, String subject, Integer amount, String timeout) {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        // 异步通知
        request.setNotifyUrl(config.notify);
        // 同步跳转页面
        request.setReturnUrl(config.redirect.url() + "?result=" + PAY_RESULT_PAGE_PARAM);
        // 配置参数
        JSONObject bizContent = new JSONObject();
        bizContent.put("subject", subject);                                     // 订单标题
        bizContent.put("out_trade_no", tradeId);                                // 我们自己生成的订单编号
        bizContent.put("total_amount", new BigDecimal(amount).divide(DIVIDE).toString());  // 订单的总金额
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");               // 固定配置
        bizContent.put("time_expire", timeout);  // 绝对超时时间

        // 5. 执行请求，拿到响应的支付宝构建的表单，返回给浏览器
        request.setBizContent(bizContent.toString());
        AlipayTradePagePayResponse response = null;
        try {
            response = client.pageExecute(request);
        } catch (AlipayApiException e) {
            logger.warn(e.getMessage());
        }
        if (response.isSuccess()) {
            return response.getBody();
        }
        return null;
    }

}
