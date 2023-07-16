package cn.qkmango.ccms.security.token;

import cn.qkmango.ccms.common.exception.handler.GlobalExceptionHandler;
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


public class JWT {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);
    //过期时间, 单位秒
    private final int expire;
    private JwtBuilder builder = new DefaultJwtBuilder();
    private JwtParser parser = new DefaultJwtParser();

    public JWT(String secret, int expire) {
        this.expire = expire;
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        builder.signWith(SignatureAlgorithm.HS256, bytes);
        parser.setSigningKey(bytes);
    }


    public String create(Account account) {
        return builder
                .claim("id", account.getId())
                .claim("role", account.getRole().name())
                .setExpiration(new Date(System.currentTimeMillis() + expire * 1000L))
                .compact();
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
