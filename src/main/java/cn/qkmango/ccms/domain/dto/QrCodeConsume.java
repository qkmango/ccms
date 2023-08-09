package cn.qkmango.ccms.domain.dto;

import cn.qkmango.ccms.domain.bo.AccountPayQrcode;

/**
 * 二维码消费
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-09 22:18
 */
public class QrCodeConsume extends AccountPayQrcode {
    private Integer amount;

    public Integer getAmount() {
        return amount;
    }

    public QrCodeConsume setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public String toString() {
        return "QrCodeConsume{" +
                "amount=" + amount +
                ", code='" + getCode() + '\'' +
                ", account=" + getAccount() +
                '}';
    }
}
