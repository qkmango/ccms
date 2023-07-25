package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.mvc.service.AuthenticationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * 授权登陆地址（认证地址）
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
     * 第三方授权登陆回调
     *
     * @param platform 平台类型
     * @param state    授权状态,防止CSRF攻击,授权状态,防止CSRF攻击,
     * @param params   authCode,error等第三方参数
     * @return 返回重定向页面, URL中包含
     * accessCode 访问码，用来请求登陆或者绑定第三方账号
     * message    提示信息
     * platform   平台类型
     * success    是否成功
     */
    @RequestMapping("{platform}/callback.do")
    public String callback(@PathVariable PlatformType platform,
                           @RequestParam String state,
                           @RequestParam Map<String, String> params) {
        return service.callback(state, platform, params);
    }

}
