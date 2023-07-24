package cn.qkmango.ccms.security.cache;

/**
 * 安全缓存
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-19 9:50
 */
public interface SecurityCache {

    /**
     * 保存 state
     *
     * @param key   key
     * @param value value
     */
    void set(String key, String value);


    void set(String key);

    /**
     * 获取 state
     *
     * @return state
     */
    String get(String key);

    /**
     * 删除 state
     *
     * @param key key
     */
    void delete(String key);


    boolean check(String key);

    /**
     * 生成 码，并保存到缓存中
     *
     * @return
     */
    String create();

    String create(String value);
}
