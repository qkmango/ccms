package cn.qkmango.ccms.security.cache;

/**
 * state 缓存
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-19 9:50
 */
public interface StateCache {

    /**
     * 保存 state
     *
     * @param key   key
     * @param value value
     */
    void setState(String key, String value);

    /**
     * 获取 state
     *
     * @return state
     */
    String getState(String key);

    /**
     * 删除 state
     *
     * @param key key
     */
    void deleteState(String key);
}
