package cn.qkmango.ccms.common.cache;

/**
 * 安全缓存
 * KM key的组成map
 * VM value的组成map
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-19 9:50
 */
public interface BaseCache<KM, VM> {

    /**
     * 保存 state
     *
     * @param key   key
     * @param value value
     */
    void set(KM key, VM value);


    VM set(KM key);

    /**
     * 获取 value
     *
     * @return value
     */
    VM get(KM key);

    /**
     * 删除 value
     *
     * @param key key
     */
    void delete(KM key);


    /**
     * 判断指定的key是否存在，并且删除
     *
     * @param key
     * @return
     */
    boolean check(KM key);

    boolean check(KM key, String value);


}
