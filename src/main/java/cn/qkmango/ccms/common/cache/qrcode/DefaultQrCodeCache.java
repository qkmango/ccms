package cn.qkmango.ccms.common.cache.qrcode;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-07 19:50
 */
public class DefaultQrCodeCache implements QrCodeCache {

    @Resource
    StringRedisTemplate template;

    //默认有效期 5 分钟,单位秒
    private long timeout = 60 * 5;

    private final String prefix;

    public DefaultQrCodeCache(String prefix, StringRedisTemplate template, long timeout) {
        this.prefix = prefix;
        this.template = template;
        this.timeout = timeout;
    }


    /**
     * 缓存
     *
     * @param key   pay:qrcode:accountId
     * @param value UUID
     */
    @Override
    public void set(Integer key, String value) {
        template.opsForValue().set(prefix + key.toString(), value, timeout, TimeUnit.SECONDS);
    }

    @Override
    public String set(Integer key) {
        throw new RuntimeException("不支持该方法");
    }

    @Override
    public String get(Integer key) {
        return template.opsForValue().get(prefix + key.toString());
    }

    @Override
    public void delete(Integer key) {
        template.delete(prefix + key.toString());
    }

    @Override
    public boolean check(Integer key) {
        String value = get(key);
        if (value == null) {
            return false;
        }
        delete(key);
        return true;
    }

    @Override
    public boolean check(Integer key, String value) {
        String cacheValue = get(key);
        //不存在返回false
        if (cacheValue == null) {
            return false;
        }

        //存在并且值也相同，删除，返回true
        if (value.equals(cacheValue)) {
            delete(key);
            return true;
        }

        //存在值但是不相同，返回false
        return false;
    }
}
