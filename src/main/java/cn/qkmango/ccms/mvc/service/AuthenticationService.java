package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.auth.PlatformType;

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



}
