package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.entity.OpenPlatform;

import java.util.List;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-25 18:02
 */
public interface OpenPlatformService {
    List<OpenPlatform> state(Integer id);

    void unbind(Integer account, PlatformType type);
}
