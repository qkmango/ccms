package cn.qkmango.ccms.security.token;

/**
 * 包含 token 和 过期时间
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-17 9:05
 */
public class TokenEntity {
    private String accessToken;
    private long expireIn;

    public TokenEntity() {
    }

    public TokenEntity(String accessToken, long expireIn) {
        this.accessToken = accessToken;
        this.expireIn = expireIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public TokenEntity setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public long getExpireIn() {
        return expireIn;
    }

    public TokenEntity setExpireIn(long expireIn) {
        this.expireIn = expireIn;
        return this;
    }

    public String toJson() {
        return "{\"accessToken\":\"" + accessToken + "\",\"expireIn\":" + expireIn + "}";
    }

    @Override
    public String toString() {
        return "TokenEntity{" +
                "accessToken='" + accessToken + '\'' +
                ", expireIn=" + expireIn +
                '}';
    }
}
