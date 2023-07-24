package cn.qkmango.ccms.security.cache;

import cn.qkmango.ccms.common.util.RedisUtil;

import java.util.UUID;

/**
 * StateCache 实现类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-19 9:51
 */
public class DefaultSecurityCache implements SecurityCache {

    private final RedisUtil redisUtil;

    //默认有效期 5 分钟,单位秒
    private long timeout = 60 * 5;

    private final String prefix;

    public DefaultSecurityCache(String prefix, RedisUtil redisUtil) {
        this.prefix = prefix;
        this.redisUtil = redisUtil;
    }

    public DefaultSecurityCache(String prefix, RedisUtil redisUtil, long timeout) {
        this.prefix = prefix;
        this.redisUtil = redisUtil;
        this.timeout = timeout;
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
        redisUtil.set(key, value, timeout);
    }

    @Override
    public void set(String key) {
        redisUtil.set(key, 1, timeout);
    }

    @Override
    public String get(String key) {
        return redisUtil.get(key);
    }

    @Override
    public void delete(String key) {
        redisUtil.delete(key);
    }

    @Override
    public boolean check(String key) {
        String value = get(key);
        if (value != null) {
            this.delete(key);
            return true;
        }
        return false;
    }

    @Override
    public String create() {
        String code = prefix + UUID.randomUUID();
        redisUtil.set(code, 1, timeout);
        return code;
    }

    @Override
    public String create(String value) {
        String code = prefix + UUID.randomUUID();
        redisUtil.set(code, value, timeout);
        return code;
    }
}
