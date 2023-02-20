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

    /**
     * 用户权限
     */
    private PermissionType permission;

    /**
     * 认证类型
     */
    private AuthenticationType type;

    /**
     * 认证用途
     */
    private AuthenticationPurpose purpose;

    /**
     * 三方平台返回的用户唯一标识
     */
    private String uid;

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "permission=" + permission +
                ", type=" + type +
                ", purpose=" + purpose +
                ", uid='" + uid + '\'' +
                '}';
    }
}
