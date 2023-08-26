package cn.qkmango.ccms.middleware.cache.qrcode;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

/**
 * 支付二维码缓存
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-07 19:50
 */
public class DefaultQrCodeCache implements QrCodeCache {

    private final StringRedisTemplate template;

    //默认有效期 5 分钟,单位秒
    private final Duration timeout;

    private final String prefix;

    public DefaultQrCodeCache(String[] prefix, StringRedisTemplate template, long timeout) {
        StringBuilder sb = new StringBuilder();
        for (String s : prefix) {
            sb.append(s).append(":");
        }
        this.prefix = sb.toString();
        this.template = template;
        this.timeout = Duration.ofSeconds(timeout);
    }

    @Override
    public String set(Integer key) {
        throw new UnsupportedOperationException("不支持的操作");
    }

    /**
     * 缓存
     *
     * @param key   pay:qrcode:accountId
     * @param value UUID
     */
    @Override
    public void set(Integer key, String value) {
        template.opsForValue().set(prefix + key.toString(), value, timeout);
    }

    @Override
    public String get(Integer key) {
        return template.opsForValue().get(prefix + key.toString());
    }

    @Override
    public boolean delete(Integer key) {
        return template.delete(prefix + key.toString());
    }

    /**
     * 检查指定的key是否存在，并且检查value是否正确
     */
    @Override
    public boolean check(Integer key, String value) {
        String cacheValue = get(key);
        //不存在
        if (cacheValue == null) {
            return false;
        }

        //比较值
        return value.equals(cacheValue);
    }

    /**
     * 检查指定的key是否存在，并且检查value是否正确,无论正确与否都删除
     */
    @Override
    public boolean checkAndDelete(Integer key, String value) {
        String cacheValue = get(key);
        //不存在
        if (cacheValue == null) {
            return false;
        }

        //存在
        delete(key);
        //比较值
        return value.equals(cacheValue);
    }

    /**
     * 检查指定的key是否存在，并且检查value是否正确,正确则删除
     */
    @Override
    public boolean checkOkDelete(Integer key, String value) {
        String cacheValue = get(key);
        //不存在
        if (cacheValue == null) {
            return false;
        }

        //存在
        //存在并且值也相同，返回true
        if (value.equals(cacheValue)) {
            delete(key);
            return true;
        }

        //存在值但是不相同，返回false
        return false;
    }
}
