package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.database.DeleteException;
import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.common.exception.database.UpdateException;
import com.alipay.api.AlipayApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 支付宝
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-24 17:03
 */
public interface AlipayService {

    /**
     * 创建支付
     *
     * @param subject     支付的名称
     * @param totalAmount 订单的总金额
     * @return R
     */
    String createPay(String subject, String totalAmount) throws InsertException;

    /**
     * 支付接口
     * subject=xxx&traceNo=xxx&totalAmount=xxx
     *
     * @param subject      支付的名称
     * @param traceNo      我们自己生成的订单编号
     * @param totalAmount  订单的总金额
     * @param httpResponse http响应
     */
    void pay(String subject, String traceNo, String totalAmount, HttpServletResponse httpResponse) throws AlipayApiException, IOException;


    /**
     * 支付结果异步通知
     *
     * @param tradeNo       支付宝交易号
     * @param outTradeNo    商家订单号, 原支付请求的商家订单号
     * @param gmtPayment    交易付款时间, 格式为 yyyy-MM-dd HH:mm:ss
     * @param receiptAmount 实收金额, 商家在交易中实际收到的款项，单位为人民币（元），精确到小数点后 2 位
     * @param sign          签名
     * @param request       http请求
     */
    void payNotify(String tradeNo,
                   String outTradeNo,
                   String gmtPayment,
                   String receiptAmount,
                   String sign,
                   HttpServletRequest request) throws AlipayApiException, UpdateException, DeleteException, JsonProcessingException;
}
