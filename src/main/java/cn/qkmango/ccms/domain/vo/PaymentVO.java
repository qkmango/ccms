package cn.qkmango.ccms.domain.vo;

import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.Payment;

import java.io.Serializable;

/**
 * 缴费项目详情展示视图对象
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-16 15:41
 */
public class PaymentVO implements Serializable {
    private Payment payment;
    private Account author;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }


    @Override
    public String toString() {
        return "PaymentVO{" +
                "payment=" + payment +
                ", author=" + author +
                '}';
    }
}
