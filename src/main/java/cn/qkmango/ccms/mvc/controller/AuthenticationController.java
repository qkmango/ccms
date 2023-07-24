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
     * @return 返回重定向页面
     */
    @RequestMapping("{platform}/callback.do")
    public String callback(@PathVariable PlatformType platform,
                           @RequestParam String state,
                           @RequestParam Map<String, String> params) {
        return service.callback(state, platform, params);
    }

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
