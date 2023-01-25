package cn.qkmango.ccms.domain.param;

import jakarta.validation.Valid;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-17 19:35
 */
public class PaymentAndRecordParam {
    @Valid
    private PaymentParam payment;
    @Valid
    private RecordParam record;

    public PaymentParam getPayment() {
        return payment;
    }

    public void setPayment(PaymentParam payment) {
        this.payment = payment;
    }

    public RecordParam getRecord() {
        return record;
    }

    public void setRecord(RecordParam record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "PaymentAndRecordParam{" +
                "payment=" + payment +
                ", record=" + record +
                '}';
    }
}
