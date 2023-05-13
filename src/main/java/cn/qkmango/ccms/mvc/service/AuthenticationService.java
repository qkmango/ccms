package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.vo.OpenPlatformBindState;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

/**
 * 第三方授权登陆
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-18 19:22
 */
public interface AuthenticationService {
    /**
     * Gitee/钉钉 授权登陆
     *
     * @param authentication 授权信息
     * @return 返回授权地址
     */
    String platformAuthenticationURL(AuthenticationAccount authentication);

    /**
     * Gitee授权回调
     * 回调中进行从Gitee获取用户信息，然后和系统数据库进行比对登陆
     *
     * @param state             授权状态,防止CSRF攻击,授权状态,防止CSRF攻击,
     *                          在redis中有效期为5分钟, 拼接为 authentication:PermissionType:UUID
     * @param code              授权码
     * @param error             有错误时返回
     * @param error_description 错误描述
     * @param request           请求
     * @param locale            语言环境
     * @return 返回重定向页面
     */
    String giteeLogin(String state, String code, String error, String error_description, HttpServletRequest request, Locale locale);

    String giteeBind(String state, String code, String error, String errorDescription, HttpServletRequest request, Locale locale) throws UpdateException;

    /**
     * 钉钉回调地址
     * 回调中进行从钉钉获取用户信息，然后和系统数据库进行比对登陆
     *
     * @param code  授权码
     * @param state 授权状态,防止CSRF攻击,授权状态,防止CSRF攻击
     * @return 返回重定向页面
     */
    String dingtalkLogin(String code, String state, Locale locale);

    String dingtalkBind(String code, String state, Locale locale) throws UpdateException;

    /**
     * 获取开放平台绑定状态
     *
     * @return 返回开放平台绑定状态
     */
    OpenPlatformBindState openPlatformState();

    /**
     * 解绑开放平台
     *
     * @param platform 平台类型
     * @return 返回解绑结果
     */
    R unbind(PlatformType platform,Locale locale) throws UpdateException;
}
