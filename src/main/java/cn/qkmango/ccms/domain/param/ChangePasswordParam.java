package cn.qkmango.ccms.domain.param;

import cn.qkmango.ccms.domain.bind.PermissionType;

import jakarta.validation.constraints.NotEmpty;

/**
 * 更改密码的参数类
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-09 22:35
 */
public class ChangePasswordParam {
    private String id;

    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String newPassword;

    private PermissionType permissionType;

    public ChangePasswordParam(String oldPassword, String newPassword, String id, PermissionType permissionType) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.id = id;
        this.permissionType = permissionType;
    }

    public ChangePasswordParam() {
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

    public PermissionType getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
    }

    @Override
    public String toString() {
        return "ChangePasswordParam{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", id=" + id +
                ", permissionType=" + permissionType +
                '}';
    }
}
