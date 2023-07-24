package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.entity.OpenPlatform;

import java.util.List;
import java.util.Map;

/**
 * 第三方授权登陆
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-18 19:22
 */
public interface AuthenticationService {
    /**
     * 获取第三方授权登陆地址
     *
     * @param authentication 授权信息
     * @return 返回授权地址
     */
    String authorize(AuthenticationAccount authentication);


    String callback(String state, PlatformType platform, Map<String, String> params);


    /**
     * 获取开放平台绑定状态
     *
     * @return 返回开放平台绑定状态
     */
    List<OpenPlatform> openPlatformState();

    /**
     * 解绑开放平台
     *
     * @param platform 平台类型
     * @return 返回解绑结果
     */
    R unbind(PlatformType platform) throws UpdateException;

}
