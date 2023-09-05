package cn.qkmango.ccms.pay;

import java.util.Map;

/**
 * 支付宝异步通知
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-25 14:21
 */
public class AlipayNotify {
    // 支付宝交易号
    public String alipayTradeNo;
    // 本系统 trade id
    public Long tradeId;
    // 交易付款时间, 格式为 yyyy-MM-dd HH:mm:ss
    public String gmtPayment;
    public String receiptAmount;
    public AlipayTradeStatus status;
    public String totalAmount;
    public String sign;

    public String getAlipayTradeNo() {
        return alipayTradeNo;
    }

    public AlipayNotify setAlipayTradeNo(String alipayTradeNo) {
        this.alipayTradeNo = alipayTradeNo;
        return this;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public AlipayNotify setTradeId(Long tradeId) {
        this.tradeId = tradeId;
        return this;
    }

    public String getGmtPayment() {
        return gmtPayment;
    }

    public AlipayNotify setGmtPayment(String gmtPayment) {
        this.gmtPayment = gmtPayment;
        return this;
    }

    public String getReceiptAmount() {
        return receiptAmount;
    }

    public AlipayNotify setReceiptAmount(String receiptAmount) {
        this.receiptAmount = receiptAmount;
        return this;
    }

    public AlipayTradeStatus getStatus() {
        return status;
    }

    public AlipayNotify setStatus(AlipayTradeStatus status) {
        this.status = status;
        return this;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public AlipayNotify setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public AlipayNotify setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public static AlipayNotify build(Map<String, String> map) {
        AlipayNotify notify = new AlipayNotify();
        notify.alipayTradeNo = map.get("trade_no");
        notify.receiptAmount = map.get("receipt_amount");
        notify.totalAmount = map.get("total_amount");
        notify.gmtPayment = map.get("gmt_payment");
        notify.tradeId = Long.parseLong(map.get("out_trade_no"));
        notify.status = AlipayTradeStatus.valueOf(map.get("trade_status"));
        notify.sign = map.get("sign");

        return notify;
    }
}
