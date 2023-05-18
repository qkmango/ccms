package cn.qkmango.ccms.security.request;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-18 22:02
 */
public class ClientIdAndSecret {
    private String id;
    private String secret;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public ClientIdAndSecret(String id, String secret) {
        this.id = id;
        this.secret = secret;
    }

    public ClientIdAndSecret() {
    }

    @Override
    public String toString() {
        return "ClientIdAndSecret{" +
                "id='" + id + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }
}
