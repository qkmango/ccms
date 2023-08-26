package cn.qkmango.ccms.middleware.cache;

/**
 * 安全缓存
 * K key的组成
 * V value的组成
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-19 9:50
 */
public interface BaseCache<K, V> {

    /**
     * 保存 state
     *
     * @param key   key
     * @param value value
     */
    void set(K key, V value);


    V set(K key);

    /**
     * 获取 value
     *
     * @return value
     */
    V get(K key);

    /**
     * 删除 value
     *
     * @param key key
     */
    boolean delete(K key);


    /**
     * 检查指定的key是否存在，并且检查value是否正确,正确则删除
     */
    boolean check(K key, V value);

    boolean checkAndDelete(K key, V value);

    boolean checkOkDelete(K key, V value);


}
