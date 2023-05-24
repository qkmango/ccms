package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.DeleteException;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.util.SnowFlake;
import cn.qkmango.ccms.common.util.UserSession;
import cn.qkmango.ccms.domain.bind.PayType;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.Pay;
import cn.qkmango.ccms.mvc.dao.PayDao;
import cn.qkmango.ccms.mvc.service.AlipayService;
import cn.qkmango.ccms.mvc.service.PayService;
import cn.qkmango.ccms.pay.AlipayConfig;
import cn.qkmango.ccms.security.request.RequestURL;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
    private PayService payService;

    @Resource
    private PayDao payDao;

    @Resource(name = "snowFlake")
    private SnowFlake sf;

    @Resource(name = "objectMapper")
    private ObjectMapper om;

    @Resource
    private ReloadableResourceBundleMessageSource ms;

    private static final RequestURL REQUEST_URL = new RequestURL("http://localhost/pay/alipay/pay.do");


    /**
     * 创建支付
     *
     * @param subject     支付的名称
     * @param totalAmount 订单的总金额
     * @param locale      语言
     * @return R
     * @throws InsertException 创建支付失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String createPay(String subject, String totalAmount, Locale locale) throws InsertException {

        //将金额转换为分
        int amount = (int) (Double.parseDouble(totalAmount) * 100);

        Account account = UserSession.getAccount();
        String user = account.getId();
        String id = String.valueOf(sf.nextId());

        Pay pay = new Pay()
                .setId(id)
                .setUser(user)
                .setDone(false)
                .setAmount(amount)
                .setType(PayType.alipay)
                .setCreateTime(new Date())
                .setInfo(subject);

        int affectedRows = payDao.insert(pay);
        if (affectedRows != 1) {
            throw new InsertException(ms.getMessage("db.insert.pay.create.failure", null, locale));
        }

        return REQUEST_URL.builder()
                .with("subject", subject)
                .with("traceNo", id)
                .with("totalAmount", totalAmount)
                .build().url();
    }

    @Override
    public void pay(String subject,
                    String traceNo,
                    String totalAmount,
                    HttpServletResponse httpResponse) throws AlipayApiException, IOException {

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(config.notify);
        JSONObject bizContent = new JSONObject();

        bizContent.put("subject", subject);                   // 订单标题
        bizContent.put("out_trade_no", traceNo);                   // 我们自己生成的订单编号
        bizContent.put("total_amount", totalAmount);               // 订单的总金额
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");  // 固定配置

        request.setBizContent(bizContent.toString());

        // 执行请求，拿到响应的结果，返回给浏览器
        String form = null;

        AlipayTradePagePayResponse response = client.pageExecute(request);
        if (!response.isSuccess()) {
            throw new RuntimeException("调用SDK生成表单失败");
        }

        form = response.getBody(); // 调用SDK生成表单

        httpResponse.setContentType("text/html;charset=UTF-8");
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void payNotify(
            String tradeNo,
            String outTradeNo,
            String gmtPayment,
            String receiptAmount,
            String sign,
            Locale locale,
            HttpServletRequest request) throws AlipayApiException, UpdateException, DeleteException, JsonProcessingException {

        //将金额转换为分
        int amount = (int) (Float.parseFloat(receiptAmount) * 100);

        int affectedRows;

        //判断是否支付成功
        //TRADE_SUCCESS 交易支付成功
        if ("TRADE_SUCCESS".equals(request.getParameter("trade_status"))) {
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }

            // 支付宝验签
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, config.alipayPublicKey, "UTF-8"); // 验证签名
            if (!checkSignature) {
                return;
            }

            Pay pay = new Pay()
                    .setId(outTradeNo)
                    .setAmount(amount)
                    .setTradeNo(tradeNo)
                    .setDone(true)

                    //TODO 这里JSON化的字符串过长，数据库中的字段长度不够，需要修改数据库字段长度
                    .setDetail(om.writeValueAsString(params));


            //更新支付信息
            affectedRows = payDao.update(pay);
            //更新失败
            //TODO 应该将结果打印到系统日志中, 以便后续手动处理
            if (affectedRows != 1) {
                throw new UpdateException(ms.getMessage("db.update.pay.failure", null, locale));
            }

            //TODO 更新用户余额
        }

        //支付失败
        else {
            affectedRows = payDao.delete(outTradeNo);
            if (affectedRows != 1) {
                throw new DeleteException(ms.getMessage("db.delete.pay.failure", null, locale));
            }
        }
    }
}
