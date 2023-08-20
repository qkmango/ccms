package cn.qkmango.ccms.domain.dto;

import jakarta.validation.constraints.NotNull;

/**
 * 注销时传递的参数
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-20 16:59
 */
public class CanceledDto {
    @NotNull
    private Integer account;
    @NotNull
    private Integer accountVersion;
    @NotNull
    private Integer cardVersion;

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Integer getAccountVersion() {
        return accountVersion;
    }

    public void setAccountVersion(Integer accountVersion) {
        this.accountVersion = accountVersion;
    }

    public Integer getCardVersion() {
        return cardVersion;
    }

    public void setCardVersion(Integer cardVersion) {
        this.cardVersion = cardVersion;
    }
}
