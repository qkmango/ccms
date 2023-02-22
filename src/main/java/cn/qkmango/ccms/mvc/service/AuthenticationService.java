package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.domain.dto.AuthenticationAccount;
import cn.qkmango.ccms.domain.vo.OpenPlatformBindState;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

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
     * Gitee授权登陆
     *
     * @param authentication 授权信息
     * @return 返回授权地址
     */
    String giteeAuth(AuthenticationAccount authentication);

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
    ModelAndView giteeLogin(String state, String code, String error, String error_description, HttpServletRequest request, Locale locale);

    /**
     * 钉钉授权登陆地址
     *
     * @return 返回授权地址
     */
    String dingtalkAuth(AuthenticationAccount authentication);

    /**
     * 钉钉回调地址
     * 回调中进行从钉钉获取用户信息，然后和系统数据库进行比对登陆
     *
     * @param code  授权码
     * @param state 授权状态,防止CSRF攻击,授权状态,防止CSRF攻击
     * @return 返回重定向页面
     */
    ModelAndView dingtalkLogin(String code, String state, HttpServletRequest request, Locale locale);

    /**
     * 获取开放平台绑定状态
     *
     * @return 返回开放平台绑定状态
     */
    OpenPlatformBindState openPlatformBindState();

    ModelAndView giteeBind(String state, String code, String error, String errorDescription, HttpServletRequest request, Locale locale) throws UpdateException;

    ModelAndView dingtalkBind(String code, String state, HttpServletRequest request, Locale locale) throws UpdateException;
}
