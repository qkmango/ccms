package cn.qkmango.ccms.domain.dto;

import cn.qkmango.ccms.common.validate.validator.Unsigned;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * 消费基类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-10 18:48
 */
public abstract class BaseConsume {

    @NotNull
    @Unsigned
    private Integer account;
    @NotNull
    @Min(1)
    private Integer amount;

    public Integer getAccount() {
        return account;
    }

    public BaseConsume setAccount(Integer account) {
        this.account = account;
        return this;
    }

    public Integer getAmount() {
        return amount;
    }

    public BaseConsume setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public String toString() {
        return "BaseConsume{" +
                "account=" + account +
                ", amount=" + amount +
                '}';
    }
}
