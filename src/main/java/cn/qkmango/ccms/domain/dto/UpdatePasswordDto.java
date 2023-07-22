package cn.qkmango.ccms.domain.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-22 15:02
 */
public class UpdatePasswordDto {

    private Integer account;
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;

    public Integer getAccount() {
        return account;
    }

    public UpdatePasswordDto setAccount(Integer account) {
        this.account = account;
        return this;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public UpdatePasswordDto setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public UpdatePasswordDto setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    @Override
    public String toString() {
        return "UpdatePasswordDto{" +
                "account='" + account + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
