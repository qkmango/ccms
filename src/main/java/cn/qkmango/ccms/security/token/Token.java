package cn.qkmango.ccms.security.token;

/**
 * 包含 token 和 过期时间
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-17 9:05
 */
public class Token {
    private String token;
    /**
     * 过期时间，单位秒
     */
    private long expireIn;
    /**
     * 过期时间戳，具体的时间点
     */
    private long expireAt;

    public Token() {
    }

    public Token(String token, long expireIn) {
        this.token = token;
        this.expireIn = expireIn;
    }

    public Token(String token, long expireIn, long expireAt) {
        this.token = token;
        this.expireIn = expireIn;
        this.expireAt = expireAt;
    }

    public String getToken() {
        return token;
    }

    public Token setToken(String token) {
        this.token = token;
        return this;
    }

    public long getExpireIn() {
        return expireIn;
    }

    public Token setExpireIn(long expireIn) {
        this.expireIn = expireIn;
        return this;
    }

    public long getExpireAt() {
        return expireAt;
    }

    public Token setExpireAt(long expireAt) {
        this.expireAt = expireAt;
        return this;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", expireIn=" + expireIn +
                ", expireAt=" + expireAt +
                '}';
    }
}
