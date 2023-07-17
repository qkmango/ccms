package cn.qkmango.ccms.security.request;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求的 URL
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-18 22:11
 */
public class RequestURL {
    private String url;

    public RequestURL(String url) {
        this.url = url;
    }

    public Builder builder() {
        return new Builder(this);
    }

    public String getUrl() {
        return this.url();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String url() {
        return url;
    }

    public static Builder builder(String url) {
        return new RequestURL.Builder(new RequestURL(url));
    }

    @Override
    public String toString() {
        return "RequestURL{" +
                "url='" + url + '\'' +
                ", builder=" + builder() +
                '}';
    }

    /**
     * Builder 模式
     *
     * @author qkmango
     * @version 1.0
     * @date 2023-05-18 22:19
     */
    public static class Builder {
        private final RequestURL url;
        public final Map<String, Object> params = new HashMap<>();

        public Builder(RequestURL requestURL) {
            this.url = requestURL;
        }

        public Builder with(String key, Object value) {
            params.put(key, value);
            return this;
        }

        public RequestURL build() {
            StringBuilder sb = new StringBuilder(url.getUrl());

            if(url.url.contains("?")) {
                sb.append("&");
            } else {
                sb.append("?");
            }
            // sb.append("?");
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
            return new RequestURL(sb.toString());
        }
    }
}
