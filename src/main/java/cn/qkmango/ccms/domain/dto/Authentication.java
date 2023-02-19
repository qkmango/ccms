package cn.qkmango.ccms.domain.dto;

import cn.qkmango.ccms.domain.bind.AuthenticationPurpose;
import cn.qkmango.ccms.domain.bind.AuthenticationType;
import cn.qkmango.ccms.domain.bind.PermissionType;

/**
 * 认证信息
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-19 10:19
 */
public class Authentication {

    private PermissionType permission;
    private AuthenticationType type;
    private AuthenticationPurpose purpose;

    public PermissionType getPermission() {
        return permission;
    }

    public void setPermission(PermissionType permission) {
        this.permission = permission;
    }

    public AuthenticationType getType() {
        return type;
    }

    public void setType(AuthenticationType type) {
        this.type = type;
    }

    public AuthenticationPurpose getPurpose() {
        return purpose;
    }

    public void setPurpose(AuthenticationPurpose purpose) {
        this.purpose = purpose;
    }


    @Override
    public String toString() {
        return "Authentication{" +
                "permission=" + permission +
                ", type=" + type +
                ", purpose=" + purpose +
                '}';
    }
}
