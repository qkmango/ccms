package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.util.UserSession;
import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.auth.PurposeType;
import cn.qkmango.ccms.domain.bind.PermissionType;
import cn.qkmango.ccms.domain.entity.OpenPlatform;
import cn.qkmango.ccms.domain.vo.OpenPlatformBindState;
import cn.qkmango.ccms.mvc.service.AuthenticationService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

/**
 * 第三方授权登陆
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-18 18:10
 */
@Controller
@RequestMapping("auth")
public class AuthenticationController {

    @Resource
    private AuthenticationService service;


    /**
     * Gitee / 钉钉 授权登陆地址（认证地址）
     *
     * @param permission 权限类型
     * @param purpose    授权目的
     * @param platform   平台类型
     * @return 返回授权地址
     */
    @ResponseBody
    @RequestMapping("{platform}/{purpose}/{permission}/authorize.do")
    @Permission({PermissionType.admin, PermissionType.user})
    public R authorize(@PathVariable PermissionType permission,
                       @PathVariable PurposeType purpose,
                       @PathVariable PlatformType platform) {

        //如果是绑定第三方平台，则说明已经登陆过了，则不信任前端传的权限，从session中获取
        if (purpose == PurposeType.bind) {
            permission = UserSession.getAccount().getPermissionType();
        }

        AuthenticationAccount authAccount = new AuthenticationAccount(permission, platform, purpose);
        String url = service.authorize(authAccount);
        return R.success().setData(url);
    }


    /**
     * Gitee授权回调
     * 进行绑定账号
     * 回调中进行从Gitee获取用户信息，然后和系统数据库进行比对登陆
     * 回调接口返回的结果必须重定向
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
    @RequestMapping("gitee/callback.do")
    public String gitee(
            @RequestParam String state,
            @RequestParam PurposeType purpose,
            String code,
            String error,
            String error_description,
            HttpServletRequest request,
            Locale locale) throws UpdateException {

        return service.giteeCallback(state, code, error, error_description, purpose, request, locale);
    }


    /**
     * 钉钉登陆回调地址
     * 进行认证登陆或者绑定账号
     * 回调中进行从钉钉获取用户信息，然后和系统数据库进行比对登陆
     *
     * @param authCode 授权码
     * @param state    授权状态,防止CSRF攻击,授权状态,防止CSRF攻击
     * @return 返回重定向页面
     */
    @RequestMapping("dingtalk/callback.do")
    public String dingtalk(
            @RequestParam PurposeType purpose,
            String authCode,
            String state,
            Locale locale) throws UpdateException {

        return service.dingtalkCallback(state, authCode, purpose, locale);
    }

    /**
     * 解绑
     */
    @ResponseBody
    @PostMapping("unbind/{platform}.do")
    public R unbind(@PathVariable PlatformType platform, Locale locale) throws UpdateException {
        return service.unbind(platform, locale);
    }

    /**
     * 获取开放平台绑定状态
     */
    @ResponseBody
    @GetMapping("open-platform/state.do")
    public R openPlatformState() {
        List<OpenPlatform> states = service.openPlatformState();
        return R.success(states);
    }
}
