package cn.qkmango.ccms.security.config;

/**
 * 支付宝配置类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-19 11:43
 */
public class AlipayAppConfig extends AppConfig {

    // 支付宝网关
    public String gateway;

    public String appPrivateKey;

    public String alipayPublicKey;

    public String getGateway() {
        return gateway;
    }

    public AlipayAppConfig setGateway(String gateway) {
        this.gateway = gateway;
        return this;
    }

    public String getAppPrivateKey() {
        return appPrivateKey;
    }

    public AlipayAppConfig setAppPrivateKey(String appPrivateKey) {
        this.appPrivateKey = appPrivateKey;
        return this;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public AlipayAppConfig setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
        return this;
    }

    @Override
    public String toString() {
        return "AlipayAppConfig{" +
                "gateway='" + gateway + '\'' +
                ", appPrivateKey='" + appPrivateKey + '\'' +
                ", alipayPublicKey='" + alipayPublicKey + '\'' +
                ", id='" + id + '\'' +
                ", secret='" + secret + '\'' +
                ", authorize=" + authorize +
                ", accessToken=" + accessToken +
                ", userInfo=" + userInfo +
                ", callback=" + callback +
                ", redirect=" + redirect +
                '}';
    }
}
