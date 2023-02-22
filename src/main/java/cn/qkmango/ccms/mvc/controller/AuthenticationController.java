package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.util.UserSession;
import cn.qkmango.ccms.domain.bind.AuthenticationPurpose;
import cn.qkmango.ccms.domain.bind.PermissionType;
import cn.qkmango.ccms.domain.bind.PlatformType;
import cn.qkmango.ccms.domain.dto.AuthenticationAccount;
import cn.qkmango.ccms.domain.vo.OpenPlatformBindState;
import cn.qkmango.ccms.mvc.service.AuthenticationService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
     * Gitee授权登陆
     *
     * @param purpose 授权目的
     * @return 返回授权地址
     */
    @ResponseBody
    @RequestMapping("gitee/auth.do")
    @Permission({PermissionType.admin, PermissionType.user})
    public R giteeAuth(@RequestParam PermissionType permission,
                       @RequestParam AuthenticationPurpose purpose) {

        //如果是绑定，则不信任前端穿的权限
        if (purpose == AuthenticationPurpose.bind) {
            permission = UserSession.getAccount().getPermissionType();
        }

        AuthenticationAccount authentication = new AuthenticationAccount(permission, PlatformType.gitee, purpose);
        String redirect = service.giteeAuth(authentication);
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
     * 钉钉授权登陆地址
     *
     * @return 返回授权地址
     */
    @ResponseBody
    @RequestMapping("dingtalk/auth.do")
    @Permission({PermissionType.admin, PermissionType.user})
    public R dingtalkAuth(@RequestParam PermissionType permission,
                          @RequestParam AuthenticationPurpose purpose) {
        AuthenticationAccount authentication = new AuthenticationAccount(permission, PlatformType.dingtalk, purpose);
        String redirect = service.dingtalkAuth(authentication);
        return R.success().setData(redirect);
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
                                      HttpServletRequest request,
                                      Locale locale) {
        return service.dingtalkLogin(code, state, request, locale);
    }

    /**
     * Gitee授权回调
     * 进行绑定账号
     * 回调中进行从Gitee获取用户信息，然后和系统数据库进行比对登陆
     *
     * @param state   授权状态,防止CSRF攻击,授权状态,防止CSRF攻击,
     *                在redis中有效期为5分钟, 拼接为 authentication:PermissionType:UUID
     * @param code    授权码
     * @param request 请求
     * @param locale  语言环境
     * @return 返回重定向页面
     */
    @RequestMapping("dingtalk/bind.do")
    public ModelAndView dingtalkBind(@RequestParam("authCode") String code,
                                     String state,
                                     HttpServletRequest request,
                                     Locale locale) throws UpdateException {
        return service.dingtalkBind(code, state, request, locale);
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
