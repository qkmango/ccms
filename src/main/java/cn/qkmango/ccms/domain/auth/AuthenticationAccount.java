package cn.qkmango.ccms.domain.auth;

import java.io.Serializable;

/**
 * 认证信息
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-19 10:19
 */
public class AuthenticationAccount implements Serializable {

    /**
     * 用户ID
     */
    private Integer account;

    /**
     * 认证平台
     */
    private PlatformType platform;

    /**
     * 三方平台返回的用户唯一标识
     */
    private String uid;

    public Integer getAccount() {
        return account;
    }

    public AuthenticationAccount setAccount(Integer account) {
        this.account = account;
        return this;
    }

    public PlatformType getPlatform() {
        return platform;
    }

    public AuthenticationAccount setPlatform(PlatformType platform) {
        this.platform = platform;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public AuthenticationAccount setUid(String uid) {
        this.uid = uid;
        return this;
    }

    @Override
    public String toString() {
        return "AuthenticationAccount{" +
                "id='" + account + '\'' +
                ", platform=" + platform +
                ", uid='" + uid + '\'' +
                '}';
    }
}
