package cn.qkmango.ccms.middleware.cache.security;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.UUID;

/**
 * StateCache 实现类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-19 9:51
 */
public class DefaultSecurityCache implements SecurityCache {

    private final StringRedisTemplate template;

    //默认有效期 5 分钟,单位秒
    private final Duration timeout;

    private final String prefix;

    public DefaultSecurityCache(String[] prefix, StringRedisTemplate template, long timeout) {
        StringBuilder sb = new StringBuilder();
        for (String s : prefix) {
            sb.append(s).append(":");
        }
        this.prefix = sb.toString();
        this.template = template;
        this.timeout = Duration.ofSeconds(timeout);
    }

    /**
     * 设置 state
     * 默认有效期 5 分钟
     *
     * @param key   key state
     * @param value value 用户信息
     */
    @Override
    public void set(String key, String value) {
        template.opsForValue().set(key, value, timeout);
    }

    @Override
    public String set(String key) {
        String value = "1";
        template.opsForValue().set(key, value, timeout);
        return value;
    }

    @Override
    public String get(String key) {
        return template.opsForValue().get(key);
    }

    @Override
    public boolean delete(String key) {
        return template.delete(key);
    }

    @Override
    public boolean check(String key, String value) {
        String cacheValue = get(key);
        return cacheValue != null && cacheValue.equals(value);
    }

    @Override
    public boolean checkAndDelete(String key) {
        String value = get(key);
        if (value != null) {
            this.delete(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkAndDelete(String key, String value) {
        String cacheValue = get(key);
        if (cacheValue == null) {
            return false;
        }
        delete(key);
        return cacheValue.equals(value);
    }

    @Override
    public boolean checkOkDelete(String key, String value) {
        String cacheValue = get(key);
        if (cacheValue == null) {
            return false;
        }

        if (cacheValue.equals(value)) {
            delete(key);
            return true;
        }
        return false;
    }

    @Override
    public String create() {
        String code = prefix + UUID.randomUUID();
        template.opsForValue().set(code, "1", timeout);
        return code;
    }

    @Override
    public String create(String value) {
        String code = prefix + UUID.randomUUID();
        template.opsForValue().set(code, value, timeout);
        return code;
    }
}
