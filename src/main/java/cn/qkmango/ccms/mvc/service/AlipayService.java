package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.pay.AlipayNotify;
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
     * @param notify 支付宝异步通知
     *               - alipayTradeNo 支付宝交易号
     *               - tradeId       本系统 trade id
     *               - gmtPayment    交易付款时间, 格式为 yyyy-MM-dd HH:mm:ss
     *               - receiptAmount 实收金额, 商家在交易中实际收到的款项，单位为人民币（元），精确到小数点后 2 位
     *               - status        交易状态
     *               - totalAmount   实收金额
     *               - sign          签名
     *               - request       http请求
     * @return
     */
    boolean notify(AlipayNotify notify, HttpServletRequest request) throws AlipayApiException;

    /**
     * 退款
     */
    void refund(Long id);
}
