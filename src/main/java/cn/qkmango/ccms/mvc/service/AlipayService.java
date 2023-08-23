package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.pay.AlipayTradeStatus;
import com.alipay.api.AlipayApiException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 支付宝
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-24 17:03
 */
public interface AlipayService {

    /**
     * 支付接口
     * subject=xxx&traceNo=xxx&totalAmount=xxx
     *
     * @param account
     * @param amount  订单的总金额，单位分
     */
    String pay(Integer account, Integer amount) throws AlipayApiException;


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
