package cn.qkmango.ccms.domain.dto;

import cn.qkmango.ccms.domain.entity.Payment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 缴费项目 Dto
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-14 17:30
 */
public class PaymentDto implements Serializable {
    @Valid
    private Payment payment;

    @NotEmpty
    private List<String> clazzIds;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }


    public List<String> getClazzIds() {
        return clazzIds;
    }

    public void setClazzIds(List<String> clazzIds) {
        this.clazzIds = clazzIds;
    }


    @Override
    public String toString() {
        return "PaymentInsert{" +
                "payment=" + payment +
                ", clazzIds=" + clazzIds +
                '}';
    }
}
