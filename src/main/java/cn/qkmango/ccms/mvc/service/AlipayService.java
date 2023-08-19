package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.domain.dto.AlipayCreatePayDto;
import cn.qkmango.ccms.pay.AlipayTradeStatus;
import com.alipay.api.AlipayApiException;
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
     */
    String createPay(AlipayCreatePayDto dto);

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
     * @param alipayTradeNo 支付宝交易号
     * @param tradeId       本系统 trade id
     * @param gmtPayment    交易付款时间, 格式为 yyyy-MM-dd HH:mm:ss
     * @param receiptAmount 实收金额, 商家在交易中实际收到的款项，单位为人民币（元），精确到小数点后 2 位
     * @param status        交易状态
     * @param totalAmount   实收金额
     * @param sign          签名
     * @param request       http请求
     */
    void payNotify(String alipayTradeNo,
                   Long tradeId,
                   String gmtPayment,
                   String receiptAmount,
                   AlipayTradeStatus status,
                   String totalAmount,
                   String sign,
                   HttpServletRequest request) throws AlipayApiException;
}
