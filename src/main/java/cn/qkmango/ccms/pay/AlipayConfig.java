package cn.qkmango.ccms.pay;

import cn.qkmango.ccms.security.request.RequestURL;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-24 14:04
 */
public class AlipayConfig {
    public String id;
    public String gateway;
    public String appPrivateKey;
    public String alipayPublicKey;
    public String notify;
    public RequestURL redirect;
    public RequestURL callback;

    public String getId() {
        return id;
    }

    public AlipayConfig setId(String id) {
        this.id = id;
        return this;
    }

    public String getGateway() {
        return gateway;
    }

    public AlipayConfig setGateway(String gateway) {
        this.gateway = gateway;
        return this;
    }

    public String getAppPrivateKey() {
        return appPrivateKey;
    }

    public AlipayConfig setAppPrivateKey(String appPrivateKey) {
        this.appPrivateKey = appPrivateKey;
        return this;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public AlipayConfig setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
        return this;
    }

    public String getNotify() {
        return notify;
    }

    public AlipayConfig setNotify(String notify) {
        this.notify = notify;
        return this;
    }

    public RequestURL getRedirect() {
        return redirect;
    }

    public AlipayConfig setRedirect(RequestURL redirect) {
        this.redirect = redirect;
        return this;
    }

    public RequestURL getCallback() {
        return callback;
    }

    public AlipayConfig setCallback(RequestURL callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public String toString() {
        return "AlipayConfig{" +
                "id='" + id + '\'' +
                ", gateway='" + gateway + '\'' +
                ", appPrivateKey='" + appPrivateKey + '\'' +
                ", alipayPublicKey='" + alipayPublicKey + '\'' +
                ", notify='" + notify + '\'' +
                ", redirect=" + redirect +
                ", callback=" + callback +
                '}';
    }
}
