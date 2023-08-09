package cn.qkmango.ccms.common.cache.captcha;

import cn.qkmango.ccms.common.util.CaptchaUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

/**
 * StateCache 实现类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-19 9:51
 */
public class DefaultCaptchaCache implements CaptchaCache {

    private final StringRedisTemplate template;

    //单位秒
    private final Duration timeout;

    private final String prefix;

    public DefaultCaptchaCache(String prefix, StringRedisTemplate template, long timeout) {
        this.prefix = prefix;
        this.template = template;
        this.timeout = Duration.ofSeconds(timeout);
    }

    @Override
    public void set(String[] key, String value) {

    }

    @Override
    public String set(String[] key) {
        String value = CaptchaUtil.generate();
        template.opsForValue().set(generateKey(key), value, timeout);
        return value;
    }

    @Override
    public String get(String[] key) {
        return template.opsForValue().get(generateKey(key));
    }

    /**
     * @param key key[0] : 账号ID
     *            key[1] : 邮箱
     * @return
     */
    @Override
    public boolean delete(String[] key) {
        return template.delete(generateKey(key));
    }

    /**
     * @param key   key[0] : 账号ID
     *              key[1] : 邮箱
     * @param value 验证码
     * @return
     */
    @Override
    public boolean check(String[] key, String value) {
        String redisValue = get(key);
        return redisValue != null && redisValue.equals(value);
    }

    @Override
    public boolean checkAndDelete(String[] key, String value) {
        String redisValue = get(key);
        if (redisValue == null) {
            return false;
        }

        delete(key);
        return redisValue.equals(value);
    }

    @Override
    public boolean checkOkDelete(String[] key, String value) {
        String redisValue = get(key);
        if (redisValue == null) {
            return false;
        }
        if (redisValue.equals(value)) {
            delete(key);
            return true;
        }
        return false;
    }


    /**
     * 生成key
     *
     * @param keymap
     * @return
     */
    private String generateKey(String[] keymap) {
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
