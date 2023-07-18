package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.common.exception.permission.LoginException;
import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.AccountInsertParam;
import cn.qkmango.ccms.domain.param.UpdatePasswordParam;
import cn.qkmango.ccms.domain.vo.AccountInfoVO;
import cn.qkmango.ccms.mvc.service.AccountService;
import cn.qkmango.ccms.mvc.service.UserService;
import cn.qkmango.ccms.security.encoder.PasswordEncoder;
import cn.qkmango.ccms.security.holder.AccountHolder;
import cn.qkmango.ccms.security.token.JWT;
import cn.qkmango.ccms.security.token.TokenEntity;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    private JWT jwt;

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
    public R<Object> login(Account account, HttpServletRequest request, HttpServletResponse response, Locale locale) throws LoginException {
        Account loginAccount = service.login(account, locale);

        //创建 TokenEntity, 包含 token 和 过期时间
        TokenEntity tokenEntity = jwt.createEntity(loginAccount);
        return R.success(
                tokenEntity,
                messageSource.getMessage("response.login.success", null, locale)
        );
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
    @GetMapping("one/current-account-info.do")
    public R currentAccountInfo() {
//        Account account = AccountHolder.getAccount();
        String id = AccountHolder.getId();
        AccountInfoVO info = service.accountInfo(id);
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

        String id = AccountHolder.getId();
        Role role = AccountHolder.getRole();

        Account account = new Account().setId(id).setRole(role);
        service.updateEmail(account, email, captcha, locale);
        return R.success(messageSource.getMessage("db.update.email.success", null, locale));
    }

    /**
     * 账户分页查询
     *
     * @param pagination
     * @return
     */
    @Permission(Role.admin)
    @PostMapping("pagination/list.do")
    public R<List<Account>> list(@RequestBody Pagination<Account> pagination) {
        return service.list(pagination);
    }

    @Permission(Role.admin)
    @PostMapping("one/insert.do")
    public R insert(@RequestBody @Validated AccountInsertParam account, Locale locale) throws InsertException {
        service.insert(account, locale);
        return R.success();
    }
}
