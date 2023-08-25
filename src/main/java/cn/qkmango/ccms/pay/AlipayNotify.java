package cn.qkmango.ccms.pay;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 支付宝异步通知
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-25 14:21
 */
public class AlipayNotify {
    @JsonProperty("alipay_trade_no")
    public String alipayTradeNo;
    public Long tradeId;
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
}
