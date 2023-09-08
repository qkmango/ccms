package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.permission.LoginException;
import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.entity.Account;

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
     * 登陆接口
     */
    Account systemLogin(Account account) throws LoginException;

    /**
     * 授权码登陆
     */
    Account accessLogin(String accessCode) throws LoginException;


    /**
     * 获取第三方授权登陆地址
     */
    String authorize(AuthenticationAccount authentication);


    String callback(String state, PlatformType platform, Map<String, String> params);


}
