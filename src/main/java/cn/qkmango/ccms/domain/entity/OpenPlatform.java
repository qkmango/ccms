package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.bind.PermissionType;

/**
 * 开放平台实体类
 * <p>第三方开放平台，用于第三方登陆</p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-22 16:57
 */
public class OpenPlatform {

    //系统用户ID
    private String id;

    // 平台名称
    private PlatformType type;

    // 平台绑定状态
    private Boolean state;

    // 平台绑定的账号
    private String uid;

    private PermissionType accountType;

    public OpenPlatform() {
    }

    public OpenPlatform(String id, PlatformType type, Boolean state, String uid, PermissionType accountType) {
        this.id = id;
        this.type = type;
        this.state = state;
        this.uid = uid;
        this.accountType = accountType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlatformType getType() {
        return type;
    }

    public void setType(PlatformType type) {
        this.type = type;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public PermissionType getAccountType() {
        return accountType;
    }

    public OpenPlatform setAccountType(PermissionType accountType) {
        this.accountType = accountType;
        return this;
    }

    @Override
    public String toString() {
        return "OpenPlatform{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", state=" + state +
                ", uid='" + uid + '\'' +
                ", accountType=" + accountType +
                '}';
    }
}