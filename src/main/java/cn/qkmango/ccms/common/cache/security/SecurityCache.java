package cn.qkmango.ccms.common.cache.security;

import cn.qkmango.ccms.common.cache.BaseCache;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-29 17:36
 */
public interface SecurityCache extends BaseCache<String, String> {
    /**
     * 生成 码，并保存到缓存中
     *
     * @return
     */
    String create();

    String create(String value);
}
