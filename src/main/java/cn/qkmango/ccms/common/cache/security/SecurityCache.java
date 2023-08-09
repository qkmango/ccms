package cn.qkmango.ccms.common.cache.security;

import cn.qkmango.ccms.common.cache.BaseCache;

/**
 * 安全相关的缓存
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-29 17:36
 */
public interface SecurityCache extends BaseCache<String, String> {

    boolean checkAndDelete(String key);

    /**
     * 生成 码，并保存到缓存中
     *
     * @return
     */
    String create();

    String create(String value);
}
