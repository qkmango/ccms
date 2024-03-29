package cn.qkmango.ccms.security.token;

import cn.qkmango.ccms.common.exception.GlobalExceptionHandler;
import cn.qkmango.ccms.domain.entity.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import io.jsonwebtoken.impl.DefaultJwtParser;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;


/**
 * JWT 工具类
 *
 * @author qkmango
 */
public class Jwt {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);
    // 过期时间, 单位秒
    private int expire;
    private final JwtBuilder builder = new DefaultJwtBuilder();
    private final JwtParser parser = new DefaultJwtParser();

    public Jwt() {
    }

    public Jwt(String secret, int expire) {
        this.expire = expire;
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        builder.signWith(SignatureAlgorithm.HS256, bytes);
        parser.setSigningKey(bytes);
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public void setSecret(String secret) {
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        builder.signWith(SignatureAlgorithm.HS256, bytes);
        parser.setSigningKey(bytes);
    }

    /**
     * 创建默认过期时间的token
     */
    public Token create(Account account) {
        long expireAt = System.currentTimeMillis() + this.expire * 1000L;
        return this.create(account, expireAt);
    }

    /**
     * 创建指定过期时间的token
     */
    public Token create(Account account, long expireAt) {
        String token = builder
                .claim("id", account.getId())
                .claim("role", account.getRole().name())
                .claim("department", account.getDepartment())
                .setExpiration(new Date(expireAt))
                .compact();

        return new Token(token, expire, expireAt);
    }

    /**
     * 解析token
     */
    public Claims parser(String token) {
        // 如果token为空，则返回null
        if (!StringUtils.hasText(token)) {
            return null;
        }

        try {
            return parser
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    public int getExpire() {
        return expire;
    }
}
