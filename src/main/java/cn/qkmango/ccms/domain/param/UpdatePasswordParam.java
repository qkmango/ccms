package cn.qkmango.ccms.domain.param;

import cn.qkmango.ccms.domain.bind.Role;

import jakarta.validation.constraints.NotEmpty;

/**
 * 更改密码的参数类
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-09 22:35
 */
public class UpdatePasswordParam {
    private String id;

    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String newPassword;

    private Role role;

    public UpdatePasswordParam(String oldPassword, String newPassword, String id, Role role) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.id = id;
        this.role = role;
    }

    public UpdatePasswordParam() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "ChangePasswordParam{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", id=" + id +
                ", role=" + role +
                '}';
    }
}
