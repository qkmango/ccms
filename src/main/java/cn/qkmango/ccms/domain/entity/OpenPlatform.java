package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.domain.auth.PlatformType;

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
    private Integer id;

    private Integer account;

    // 平台名称
    private PlatformType type;

    // 平台绑定的账号
    private String uid;

    public Integer getId() {
        return id;
    }

    public OpenPlatform setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getAccount() {
        return account;
    }

    public OpenPlatform setAccount(Integer account) {
        this.account = account;
        return this;
    }

    public PlatformType getType() {
        return type;
    }

    public OpenPlatform setType(PlatformType type) {
        this.type = type;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public OpenPlatform setUid(String uid) {
        this.uid = uid;
        return this;
    }

    @Override
    public String toString() {
        return "OpenPlatform{" +
                "id=" + id +
                ", account=" + account +
                ", type=" + type +
                ", uid='" + uid + '\'' +
                '}';
    }
}
