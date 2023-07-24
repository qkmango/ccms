package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.entity.OpenPlatform;
import cn.qkmango.ccms.mvc.service.AuthenticationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 第三方授权登陆
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-18 18:10
 */
@Validated
@Controller
@RequestMapping("auth")
public class AuthenticationController {

    @Resource
    private AuthenticationService service;


    /**
     * Gitee / 钉钉 / 支付宝 授权登陆地址（认证地址）
     *
     * @param platform 平台类型
     * @return 返回授权地址
     */
    @ResponseBody
    @RequestMapping("{platform}/authorize.do")
    @Permission({Role.admin, Role.user})
    public R authorize(@PathVariable PlatformType platform) {

        AuthenticationAccount authAccount = new AuthenticationAccount();
        authAccount.setPlatform(platform);
        String url = service.authorize(authAccount);
        return R.success().setData(url);
    }


    /**
     * @param platform 平台类型
     * @param state    授权状态,防止CSRF攻击,授权状态,防止CSRF攻击,
     * @param params   authCode,error等第三方参数
     * @return
     */
    @ResponseBody
    @RequestMapping("/{platform}/callback.do")
    public R callback(@PathVariable PlatformType platform,
                      @RequestParam String state,
                      @RequestParam Map<String, String> params) {
        String authCode = null;

        switch (platform) {
            case gitee -> authCode = service.giteeCallback(state, params);
            case dingtalk -> authCode = service.dingtalkCallback(state, params);
            case alipay -> authCode = service.alipayCallback(state, params);
        }

        if (authCode != null) {
            return R.success().setData(authCode);
        }
        return R.fail();
    }


    /**
     * Gitee授权回调
     * 进行绑定账号
     * 回调中进行从Gitee获取用户信息，然后和系统数据库进行比对登陆
     * 回调接口返回的结果必须重定向
     *
     * @param state            授权状态,防止CSRF攻击,授权状态,防止CSRF攻击,
     *                         在redis中有效期为5分钟, 拼接为 authentication:Role:UUID
     * @param code             授权码
     * @param error            有错误时返回
     * @param errorDescription 错误描述
     * @return 授权码
     */
    // @ResponseBody
    // @RequestMapping("gitee/callback.do")
    // public R gitee(
    //         @RequestParam String state,
    //         @RequestParam String code,
    //         @RequestParam(required = false) String error,
    //         @RequestParam(required = false, value = "error_description") String errorDescription) throws UpdateException {
    //
    //     String authCode = service.giteeCallback(state, code, error, errorDescription);
    //     if (authCode != null) {
    //         return R.success().setData(authCode);
    //     }
    //     return R.fail();
    // }


    /**
     * 钉钉登陆回调地址
     * 进行认证登陆或者绑定账号
     * 回调中进行从钉钉获取用户信息，然后和系统数据库进行比对登陆
     *
     * @param authCode 授权码
     * @param state    授权状态,防止CSRF攻击,授权状态,防止CSRF攻击
     * @return 返回重定向页面
     */
    // @RequestMapping("dingtalk/callback.do")
    // public String dingtalk(
    //         @RequestParam("authCode") String code,
    //         @RequestParam String state) throws UpdateException {
    //
    //     return service.dingtalkCallback(state, code);
    //
    // }

    /**
     * 支付宝登陆回调地址
     *
     * @param purpose  授权目的 callback 自定义拼接的参数
     * @param authCode 授权码
     * @param state    授权状态,防止CSRF攻击,授权状态,防止CSRF攻击
     * @param appId    应用id
     * @param source   应用来源
     * @param scope    授权范围
     * @return 返回重定向页面
     */
    // @RequestMapping("alipay/callback.do")
    // public String alipay(
    //         @RequestParam("auth_code") String code,
    //         @RequestParam String state,
    //         @RequestParam("app_id") String appId,
    //         @RequestParam String source,
    //         @RequestParam String scope) throws UpdateException {
    //
    //     return service.alipayCallback(state, code, appId, source, scope);
    // }


    /**
     * 解绑
     */
    @ResponseBody
    @PostMapping("unbind/{platform}.do")
    public R unbind(@PathVariable PlatformType platform) throws UpdateException {
        return service.unbind(platform);
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
