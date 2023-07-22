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
    //过期时间, 单位秒
    private final int expire;
    private final JwtBuilder builder = new DefaultJwtBuilder();
    private final JwtParser parser = new DefaultJwtParser();

    public Jwt(String secret, int expire) {
        this.expire = expire;
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        builder.signWith(SignatureAlgorithm.HS256, bytes);
        parser.setSigningKey(bytes);
    }

    /**
     * 创建指定过期时间的token
     *
     * @param account
     * @param expireIn
     * @return
     */
    public String create(Account account, long expireIn) {
        return builder
                .claim("id", account.getId())
                .claim("role", account.getRole().name())
                .setExpiration(new Date(expireIn))
                .compact();
    }


    /**
     * 创建默认过期时间的token
     *
     * @param account
     * @return
     */
    public String create(Account account) {
        long expireIn = System.currentTimeMillis() + this.expire * 1000L;
        return this.create(account, expireIn);
    }

    /**
     * 创建指定过期时间的 TokenEntity
     *
     * @param account
     * @param expireIn
     * @return TokenEntity, 包含token和过期时间
     */
    public TokenEntity createEntity(Account account, long expireIn) {
        String token = create(account, expireIn);
        return new TokenEntity(token, expireIn);
    }

    /**
     * 创建 默认过期时间的 TokenEntity
     *
     * @param account
     * @return TokenEntity, 包含token和过期时间
     */
    public TokenEntity createEntity(Account account) {
        long expireIn = System.currentTimeMillis() + this.expire * 1000L;
        String token = create(account, expireIn);
        return new TokenEntity(token, expireIn);
    }

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

    /**
     * 获取过期时间长度
     * 单位 秒
     *
     * @return
     */
    public int getExpire() {
        return expire;
    }

}
