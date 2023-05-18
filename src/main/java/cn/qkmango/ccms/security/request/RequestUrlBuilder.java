package cn.qkmango.ccms.security.request;

import org.bouncycastle.cert.ocsp.Req;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-18 22:19
 */
public class RequestUrlBuilder {
    private RequestURL url;
    public Map<String,String> params = new HashMap<>();
    public RequestUrlBuilder(RequestURL requestURL) {
        this.url = requestURL;
    }

    public RequestUrlBuilder with(String key, String value) {
        params.put(key, value);
        return this;
    }

    public String build() {
        StringBuilder sb = new StringBuilder(url.getUrl());
        sb.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public RequestURL buildRequestURL() {
        StringBuilder sb = new StringBuilder(url.getUrl());
        sb.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return new RequestURL(sb.toString());
    }
}
