package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.util.UserSession;
import cn.qkmango.ccms.domain.auth.PurposeType;
import cn.qkmango.ccms.domain.bind.PermissionType;
import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.vo.OpenPlatformBindState;
import cn.qkmango.ccms.mvc.service.AuthenticationService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

/**
 * 第三方授权登陆
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-18 18:10
 */
@Controller
@RequestMapping("authentication")
public class AuthenticationController {

    @Resource
    private AuthenticationService service;


    /**
     * Gitee / 钉钉 授权登陆地址
     *
     * @param permission 权限类型
     * @param purpose    授权目的
     * @param platform   平台类型
     * @return 返回授权地址
     */
    @ResponseBody
    @RequestMapping("{platform}/{purpose}/{permission}/auth.do")
    @Permission({PermissionType.admin, PermissionType.user})
    public R auth(@PathVariable PermissionType permission,
                  @PathVariable PurposeType purpose,
                  @PathVariable PlatformType platform) {

        //如果是绑定，则不信任前端穿的权限
        if (purpose == PurposeType.bind) {
            permission = UserSession.getAccount().getPermissionType();
        }

        AuthenticationAccount authentication = new AuthenticationAccount(permission, platform, purpose);
        String redirect = service.auth(authentication);
        return R.success().setData(redirect);
    }

    /**
     * Gitee授权回调
     * 进行认证登陆或者绑定账号
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
    @RequestMapping("gitee/login.do")
    public ModelAndView giteeLogin(@RequestParam String state,
                                   String code,
                                   String error,
                                   String error_description,
                                   HttpServletRequest request,
                                   Locale locale) {
        return service.giteeLogin(state, code, error, error_description, request, locale);
    }


    /**
     * Gitee授权回调
     * 进行绑定账号
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
    @RequestMapping("gitee/bind.do")
    public ModelAndView giteeBind(@RequestParam String state,
                                  String code,
                                  String error,
                                  String error_description,
                                  HttpServletRequest request,
                                  Locale locale) throws UpdateException {
        return service.giteeBind(state, code, error, error_description, request, locale);
    }


    /**
     * Gitee授权回调
     * 进行绑定账号
     * 回调中进行从Gitee获取用户信息，然后和系统数据库进行比对登陆
     *
     * @param purpose           授权目的
     * @param state             授权状态,防止CSRF攻击,授权状态,防止CSRF攻击,
     *                          在redis中有效期为5分钟, 拼接为 authentication:PermissionType:UUID
     * @param code              授权码
     * @param error             有错误时返回
     * @param error_description 错误描述
     * @param request           请求
     * @param locale            语言环境
     * @return 返回重定向页面
     */
    @RequestMapping("gitee/{purpose}.do")
    public ModelAndView gitee(
            @PathVariable PurposeType purpose,
            @RequestParam String state,
            String code,
            String error,
            String error_description,
            HttpServletRequest request,
            Locale locale) throws UpdateException {

        switch (purpose) {
            case login:
                return service.giteeLogin(state, code, error, error_description, request, locale);
            case bind:
                return service.giteeBind(state, code, error, error_description, request, locale);
            default:
                return new ModelAndView("redirect:/");
        }
    }


    /**
     * 钉钉登陆回调地址
     * 进行认证登陆或者绑定账号
     * 回调中进行从钉钉获取用户信息，然后和系统数据库进行比对登陆
     *
     * @param code  授权码
     * @param state 授权状态,防止CSRF攻击,授权状态,防止CSRF攻击
     * @return 返回重定向页面
     */
    @RequestMapping("dingtalk/login.do")
    public ModelAndView dingtalkLogin(@RequestParam("authCode") String code,
                                      String state,
                                      Locale locale) {
        return service.dingtalkLogin(code, state, locale);
    }

    /**
     * Gitee授权回调
     * 进行绑定账号
     * 回调中进行从Gitee获取用户信息，然后和系统数据库进行比对登陆
     *
     * @param state  授权状态,防止CSRF攻击,授权状态,防止CSRF攻击,
     *               在redis中有效期为5分钟, 拼接为 authentication:PermissionType:UUID
     * @param code   授权码
     * @param locale 语言环境
     * @return 返回重定向页面
     */
    @RequestMapping("dingtalk/bind.do")
    public ModelAndView dingtalkBind(@RequestParam("authCode") String code,
                                     String state,
                                     Locale locale) throws UpdateException {
        return service.dingtalkBind(code, state, locale);
    }

    @ResponseBody
    @PostMapping("{platform}/unbind.do")
    public R unbind(@PathVariable PlatformType platform, Locale locale) throws UpdateException {
        return service.unbind(platform, locale);
    }

    /**
     * 获取开放平台绑定状态
     *
     * @return 返回开放平台绑定状态
     */
    @ResponseBody
    @GetMapping("open-platform/state.do")
    public R openPlatformBindState() {
        OpenPlatformBindState state = service.openPlatformBindState();
        return R.success(state);
    }
}
