package cn.qkmango.ccms.security.request;

/**
 * 第三方平台 App 信息
 * 存储 AppId 和 AppSecret
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-18 22:02
 */
public class AuthApp {
    private String id;
    private String secret;

    public AuthApp(String id, String secret) {
        this.id = id;
        this.secret = secret;
    }

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public String toString() {
        return "AuthApp{" +
                "id='" + id + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }
}
