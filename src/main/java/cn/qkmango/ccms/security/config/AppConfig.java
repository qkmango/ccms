package cn.qkmango.ccms.security.config;

import cn.qkmango.ccms.security.request.RequestURL;

/**
 * 第三方平台配置类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-19 11:22
 */
public class AppConfig {


    // 第三方平台的 id 和 secret
    public String id;
    public String secret;
    public RequestURL authorize;
    public RequestURL accessToken;
    public RequestURL userInfo;
    public RequestURL callback;
    public RequestURL redirect;

    public String getId() {
        return id;
    }

    public AppConfig setId(String id) {
        this.id = id;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    public AppConfig setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    // 以下为 URL 的 getter 和 setter 方法
    public RequestURL getAuthorize() {
        return authorize;
    }

    public AppConfig setAuthorize(String authorize) {
        this.authorize = new RequestURL(authorize);
        return this;
    }

    public RequestURL getAccessToken() {
        return accessToken;
    }

    public AppConfig setAccessToken(String accessToken) {
        this.accessToken = new RequestURL(accessToken);
        return this;
    }

    public RequestURL getUserInfo() {
        return userInfo;
    }

    public AppConfig setUserInfo(String userInfo) {
        this.userInfo = new RequestURL(userInfo);
        return this;
    }

    public RequestURL getCallback() {
        return callback;
    }

    public AppConfig setCallback(String callback) {
        this.callback = new RequestURL(callback);
        return this;
    }

    public RequestURL getRedirect() {
        return redirect;
    }

    public AppConfig setRedirect(String redirect) {
        this.redirect = new RequestURL(redirect);
        return this;
    }


}
