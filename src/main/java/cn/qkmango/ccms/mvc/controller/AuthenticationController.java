package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.permission.LoginException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.validate.group.Query;
import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.vo.LoginResult;
import cn.qkmango.ccms.mvc.service.AuthenticationService;
import cn.qkmango.ccms.security.holder.AccountHolder;
import cn.qkmango.ccms.security.token.Jwt;
import cn.qkmango.ccms.security.token.Token;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Value("${spring.mvc.servlet.path}")
    private String contextPath;

    @Resource
    private Jwt jwt;

    @Resource
    private ReloadableResourceBundleMessageSource ms;


    /**
     * 使用账户密码登陆
     */
    @ResponseBody
    @PostMapping("system-login.do")
    public R<LoginResult> systemLogin(@Validated(Query.Login.class) Account account) throws LoginException {
        Account loginAccount = service.systemLogin(account);

        Token token = jwt.create(loginAccount);
        LoginResult result = new LoginResult(loginAccount, token);
        String message = ms.getMessage("response.login.success", null, LocaleContextHolder.getLocale());

        return R.success(result, message);
    }


    /**
     * 使用授权码登陆
     * <p>授权码为第三方认证后回调 {@link cn.qkmango.ccms.mvc.controller.AuthenticationController#callback(PlatformType, String, Map)}
     * 时返回的重定向URL中的授权码</p>
     */
    @ResponseBody
    @PostMapping("access-login.do")
    public R<LoginResult> accessLogin(@NotBlank String accessCode) throws LoginException {
        Account loginAccount = service.accessLogin(accessCode);

        Token token = jwt.create(loginAccount);
        LoginResult result = new LoginResult(loginAccount, token);
        String message = ms.getMessage("response.login.success", null, LocaleContextHolder.getLocale());

        return R.success(result, message);
    }

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


    /**
     * 测试是否登陆
     */
    @ResponseBody
    @RequestMapping("ping.do")
    public R<Boolean> ping() {
        Account account = AccountHolder.getAccount();
        return account == null ? R.success(false) : R.success(true);
    }

}
