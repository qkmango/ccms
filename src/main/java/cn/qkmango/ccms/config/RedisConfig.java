package cn.qkmango.ccms.config;

import cn.qkmango.ccms.middleware.cache.captcha.DefaultCaptchaCache;
import cn.qkmango.ccms.middleware.cache.qrcode.DefaultQrCodeCache;
import cn.qkmango.ccms.middleware.cache.security.DefaultSecurityCache;
import cn.qkmango.ccms.middleware.cache.security.SecurityCache;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis 配置类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-09 19:46
 */
@Configuration
public class RedisConfig {


    /**
     * 自定义RedisTemplate的数据类型
     * 主要配置的就是序列化机制
     * 更改它的序列化器为Json格式的序列化机制
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        /*定义方法的返回值,泛型自动匹配，后面的可以省略*/
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        /*将key与hashKey设置为String的序列化方式*/
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // 第三方认证时防止 CSRF的 state 缓存工具
    @Bean("authStateCache")
    public SecurityCache authStateCache() {
        return new DefaultSecurityCache(new String[]{"auth", "state"}, stringRedisTemplate, 60 * 5);
    }

    // 第三方认证成功后系统生成的 access_code 缓存工具
    @Bean("authAccessCodeCache")
    public SecurityCache authAccessCodeCache() {
        return new DefaultSecurityCache(new String[]{"auth", "access_code"}, stringRedisTemplate, 60 * 5);
    }

    // 验证码缓存工具
    @Bean("captchaCache")
    public DefaultCaptchaCache captchaCache() {
        return new DefaultCaptchaCache(new String[]{"captcha", "email"}, stringRedisTemplate, 60 * 5);
    }

    // 二维码缓存工具
    @Bean("qrCodeCache")
    public DefaultQrCodeCache qrCodeCache() {
        return new DefaultQrCodeCache(new String[]{"pay", "qrcode"}, stringRedisTemplate, 60 * 5);
    }

}
