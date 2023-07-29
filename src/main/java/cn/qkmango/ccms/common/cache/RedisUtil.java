package cn.qkmango.ccms.common.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis 缓存工具类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-14 16:07
 */
@Component("redisUtil")
public class RedisUtil {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    @Resource(name = "objectMapper")
    private ObjectMapper objectMapper;

    /**
     * 将对象存入redis
     *
     * @param key   redis的key
     * @param value 存入的对象
     */
    public void set(String key, Object value) {
        try {
            // 将对象转换为json字符串，存入redis
            stringRedisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将对象存入redis
     *
     * @param key     redis的key
     * @param value   存入的对象
     * @param timeout 过期时间 单位：秒
     */
    public void set(String key, Object value, long timeout) {
        try {
            // 将对象转换为json字符串，存入redis
            String jsonValue;
            if (value instanceof String) {
                jsonValue = (String) value;
            } else {
                jsonValue = objectMapper.writeValueAsString(value);
            }

            stringRedisTemplate.opsForValue().set(key, jsonValue, timeout, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 从redis中获取对象
     *
     * @param key   redis的key
     * @param clazz 对象的类型
     * @param <T>   对象的类型
     * @return 对象
     */
    public <T> T get(String key, Class<T> clazz) {
        try {
            // 从redis中获取json字符串，转换为对象返回
            String value = stringRedisTemplate.opsForValue().get(key);
            if (value == null) {
                return null;
            }
            return objectMapper.readValue(value, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取redis中的对象，JSON形式字符串
     *
     * @param key redis的key
     * @return 对象的JSON形式字符串
     */
    public String get(String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 模糊查询key
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }


    /**
     * 删除redis中的对象
     *
     * @param keys
     * @return
     */
    public int delete(String... keys) {
        int count = 0;
        for (String key : keys) {
            Boolean delete = stringRedisTemplate.delete(key);
            if (delete != null && delete) {
                count++;
            }
        }
        return count;
    }

    /**
     * 模糊删除redis中的对象
     *
     * @param patterns
     * @return
     */
    public long patternDelete(String... patterns) {
        long count = 0;
        for (String pattern : patterns) {
            Set<String> keys = stringRedisTemplate.keys(pattern);
            Long delete = stringRedisTemplate.delete(keys);
            if (delete != null) {
                count += delete;
            }
        }
        return count;
    }

    /**
     * 判断是否存在key
     */
    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

}
