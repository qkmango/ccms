package cn.qkmango.ccms.pay;

public class AliPay {
    private String traceNo;
    private double totalAmount;
    private String subject;
    private String alipayTraceNo;

    public String getTraceNo() {
        return traceNo;
    }

    public AliPay setTraceNo(String traceNo) {
        this.traceNo = traceNo;
        return this;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public AliPay setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public AliPay setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getAlipayTraceNo() {
        return alipayTraceNo;
    }

    public AliPay setAlipayTraceNo(String alipayTraceNo) {
        this.alipayTraceNo = alipayTraceNo;
        return this;
    }
}
