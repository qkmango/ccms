package cn.qkmango.ccms.security.config;

import cn.qkmango.ccms.security.request.RequestURL;

import java.util.HashMap;
import java.util.Map;

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

    // 第三方平台的请求地址
    public Map<String, RequestURL> urls = new HashMap<>();

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

    public AppConfig add(String key, String url) {
        urls.put(key, new RequestURL(url));
        return this;
    }

    public RequestURL get(String key) {
        return urls.get(key);
    }

    public RequestURL accessToken() {
        return urls.get("accessToken");
    }

    public RequestURL userInfo() {
        return urls.get("userInfo");
    }

    public RequestURL authorize() {
        return urls.get("authorize");
    }

    public RequestURL redirect() {
        return urls.get("redirect");
    }

}
