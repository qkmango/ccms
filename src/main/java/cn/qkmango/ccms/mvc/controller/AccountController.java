package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.LoginException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.security.encoder.PasswordEncoder;
import cn.qkmango.ccms.common.util.UserSession;
import cn.qkmango.ccms.common.validate.group.Query;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.param.UpdatePasswordParam;
import cn.qkmango.ccms.domain.vo.AccountInfoVO;
import cn.qkmango.ccms.mvc.service.AccountService;
import cn.qkmango.ccms.mvc.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

/**
 * 账户控制器
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 14:57
 */

@Validated
@RestController
@RequestMapping("account")
public class AccountController {
    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private AccountService service;

    @Resource
    private UserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 登陆
     *
     * @param account 用户对象
     * @param request HTTP请求
     * @param locale  语言环境
     * @return 登陆结果
     * @throws LoginException 登陆异常登陆失败
     */
    @PostMapping("login.do")
    public R<Object> login(Account account, HttpServletRequest request, Locale locale) throws LoginException {
        Account loginAccount = service.login(account, locale);
        request.getSession(true).setAttribute("account", loginAccount);
        return R.success(messageSource.getMessage("response.login.success", null, locale));
    }

    /**
     * 退出
     *
     * @param request HTTP请求
     * @param locale  语言环境
     * @return 结果
     */
    @PostMapping("logout.do")
    public R<Object> logout(HttpServletRequest request, Locale locale) {
        request.getSession().invalidate();
        return R.success(messageSource.getMessage("response.logout.success", null, locale));
    }

    /**
     * 修改密码
     *
     * @param param   新的密码
     * @param session 会话
     * @param locale  语言环境
     * @return 结果
     * @throws UpdateException 修改失败
     */

    @PostMapping("update/password.do")
    public R<Object> updatePassword(@Validated UpdatePasswordParam param, HttpSession session, Locale locale) throws UpdateException {

        Account account = (Account) session.getAttribute("account");
        param.setRole(account.getRole());
        param.setId(account.getId());

        service.updatePassword(param, locale);

        return R.success(messageSource.getMessage("db.update.password.success", null, locale));
    }


    /**
     * 重置密码
     * 重置密码为身份证后6位
     *
     * @param account 账户 ID
     * @param locale  语言环境
     * @return 响应结果
     * @throws UpdateException 更新异常
     */
    @Permission(Role.admin)
    @PostMapping(value = "update/resetPassword.do")
    public R<Object> resetPassword(@NotEmpty String account, Locale locale) throws UpdateException {
//        userService.resetPassword(card, locale);
//        service.resetPassword(account);
        service.resetPassword(account, locale);
        return R.success(messageSource.getMessage("db.resetPassword.success", null, locale));
    }


    /**
     * 注销账户
     *
     * @param account 账户
     * @param locale  语言环境
     * @return 响应结果
     * @throws UpdateException 更新异常
     */
    @Permission({Role.admin, Role.user})
    @PostMapping(value = "update/canceled.do")
    public R<Object> canceled(@NotEmpty String account, Locale locale) throws UpdateException {
        // userService.canceled(card, locale);
        service.canceled(account, locale);
        return R.success(messageSource.getMessage("db.account.unsubscribe.success", null, locale));
    }

    /**
     * 获取当前登陆账户信息
     *
     * @return 用户信息
     */
//    @Permission({Role.admin, Role.user})
    @GetMapping("one/current-account-info.do")
    public R currentAccountInfo() {
        Account account = UserSession.getAccount();
        AccountInfoVO info = service.accountInfo(account.getId());
        return R.success(info);
    }

    /**
     * 获取用户账户详情
     * 功能和 {@link AccountController#currentAccountInfo()} 一样，只是做了权限控制
     *
     * @param account 账户ID
     * @return
     */
    @Permission(Role.admin)
    @GetMapping("one/account-info.do")
    public R accountInfo(@NotEmpty String account, Locale locale) {
        AccountInfoVO info = service.accountInfo(account);
        return R.success(info);
    }

    /**
     * 更新用户email
     *
     * @param email  新的email
     * @param locale 语言环境
     * @return 修改结果
     * @throws UpdateException 修改失败
     */
    @Permission({Role.admin, Role.user})
    @PostMapping("user/update/email.do")
    public R updateEmail(@NotBlank(message = "{valid.email.notBlank}")
                         @Email(message = "{valid.email.illegal}") String email,
                         @Pattern(regexp = "^[a-zA-Z0-9]{5}$", message = "{valid.captcha.illegal}") String captcha,
                         Locale locale) throws UpdateException {
        Account account = UserSession.getAccount();
        service.updateEmail(account, email, captcha, locale);
        return R.success(messageSource.getMessage("db.update.email.success", null, locale));
    }

    /**
     * 同组用户列表
     *
     * @return 同组用户列表
     */
    @GetMapping("group-user/all/list.do")
    public R<List> groupUser() {
        List<Account> data = service.groupUser();
        return R.success(data);
    }

}
