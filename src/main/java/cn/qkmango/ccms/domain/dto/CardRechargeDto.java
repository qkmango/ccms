package cn.qkmango.ccms.domain.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

/**
 * 充值DTO
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-19 11:27
 */
public class CardRechargeDto {
    @NotNull
    private Integer account;

    @NotNull
    @Range(min = 100, max = 100000, message = "充值金额在1~1000元")
    private Integer amount;

    @NotNull
    private Integer version;

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
