package cn.qkmango.ccms.security.cache;

import cn.qkmango.ccms.common.util.RedisUtil;

/**
 * 默认的 StateCache 实现类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-19 9:51
 */
public class DefaultStateCache implements StateCache {

    private RedisUtil redisUtil;

    //默认有效期 5 分钟,单位秒
    private long timeout = 60 * 5;

    public DefaultStateCache(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public DefaultStateCache(RedisUtil redisUtil, long timeout) {
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
    public void setState(String key, String value) {
        redisUtil.set(key, value, timeout);
    }

    @Override
    public String getState(String key) {
        return redisUtil.get(key);
    }

    @Override
    public void deleteState(String key) {
        redisUtil.delete(key);
    }
}
