package cn.qkmango.ccms.domain.auth;

import cn.qkmango.ccms.domain.bind.PermissionType;

/**
 * 认证信息
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-19 10:19
 */
public class AuthenticationAccount {

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户权限
     */
    private PermissionType permission;

    /**
     * 认证平台
     */
    private PlatformType platform;

    /**
     * 认证用途
     */
    private PurposeType purpose;

    /**
     * 三方平台返回的用户唯一标识
     */
    private String uid;

    public AuthenticationAccount() {
    }

    public AuthenticationAccount(PermissionType permission, PlatformType platform, PurposeType purpose) {
        this.permission = permission;
        this.platform = platform;
        this.purpose = purpose;
    }

    public AuthenticationAccount(PermissionType permission, PlatformType platform, PurposeType purpose, String uid) {
        this.permission = permission;
        this.platform = platform;
        this.purpose = purpose;
        this.uid = uid;
    }

    public PermissionType getPermission() {
        return permission;
    }

    public void setPermission(PermissionType permission) {
        this.permission = permission;
    }

    public PlatformType getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformType platform) {
        this.platform = platform;
    }

    public PurposeType getPurpose() {
        return purpose;
    }

    public void setPurpose(PurposeType purpose) {
        this.purpose = purpose;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "AuthenticationAccount{" +
                "id='" + id + '\'' +
                ", permission=" + permission +
                ", platform=" + platform +
                ", purpose=" + purpose +
                ", uid='" + uid + '\'' +
                '}';
    }
}
