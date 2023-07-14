package cn.qkmango.ccms.domain.vo;

import cn.qkmango.ccms.domain.entity.Payment;

import java.io.Serializable;

/**
 * 缴费记录 vo
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-17 18:59
 */
public class RecordVO implements Serializable {
    private Record record;
    private Payment payment;

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }


    @Override
    public String toString() {
        return "RecordVO{" +
                "record=" + record +
                ", payment=" + payment +
                '}';
    }
}
