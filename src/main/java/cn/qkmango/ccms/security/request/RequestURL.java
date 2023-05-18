package cn.qkmango.ccms.security.request;

/**
 * 请求的 URL
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-18 22:11
 */
public class RequestURL {
    private String url;
    // private Map<String, String> params = new HashMap<>();

    // public RequestURL() {
    // }

    public RequestURL(String url) {
        this.url = url;
    }

    public RequestUrlBuilder builder() {
        return new RequestUrlBuilder(this);
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


    @Override
    public String toString() {
        return "RequestURL{" +
                "url='" + url + '\'' +
                ", builder=" + builder() +
                '}';
    }
}
