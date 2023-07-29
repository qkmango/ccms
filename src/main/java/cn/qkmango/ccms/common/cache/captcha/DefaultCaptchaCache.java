package cn.qkmango.ccms.common.cache.captcha;

import cn.qkmango.ccms.common.cache.RedisUtil;
import cn.qkmango.ccms.common.util.CaptchaUtil;

/**
 * StateCache 实现类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-19 9:51
 */
public class DefaultCaptchaCache implements CaptchaCache {

    private final RedisUtil redisUtil;

    //默认有效期 5 分钟,单位秒
    private long timeout = 60 * 5;

    private final String prefix;

    public DefaultCaptchaCache(String prefix, RedisUtil redisUtil) {
        this.prefix = prefix;
        this.redisUtil = redisUtil;
    }

    public DefaultCaptchaCache(String prefix, RedisUtil redisUtil, long timeout) {
        this.prefix = prefix;
        this.redisUtil = redisUtil;
        this.timeout = timeout;
    }

    @Override
    public void set(String[] keymap, String value) {

    }

    @Override
    public String set(String[] keymap) {
        String key = generateKey(keymap);
        String value = CaptchaUtil.generate();
        redisUtil.set(key, value, timeout);
        return value;
    }

    @Override
    public String get(String[] keymap) {
        return redisUtil.get(generateKey(keymap));
    }

    /**
     * @param keymap keymap[0] : 账号ID
     *               keymap[1] : 邮箱
     * @param keymap key
     */
    @Override
    public void delete(String[] keymap) {
        redisUtil.delete(generateKey(keymap));
    }

    @Override
    public boolean check(String[] keymap) {
        String value = get(keymap);
        if (value != null) {
            delete(keymap);
            return true;
        }
        return false;
    }

    /**
     * @param keymap keymap[0] : 账号ID
     *               keymap[1] : 邮箱
     * @param value  验证码
     * @return
     */
    @Override
    public boolean check(String[] keymap, String value) {
        String redisValue = get(keymap);
        if (redisValue != null && redisValue.equals(value)) {
            delete(keymap);
            return true;
        }
        return false;
    }

    @Override
    public String generateKey(String[] keymap) {
        if (keymap == null || keymap.length == 0) {
            return null;
        }
        StringBuilder key = new StringBuilder(prefix);
        for (String s : keymap) {
            key.append(":").append(s);
        }
        return key.toString();
    }
}
