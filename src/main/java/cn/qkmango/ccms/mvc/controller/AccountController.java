package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.exception.permission.LoginException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.dto.AccountInsertDto;
import cn.qkmango.ccms.domain.dto.UpdatePasswordDto;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.vo.AccountDetailVO;
import cn.qkmango.ccms.mvc.service.AccountService;
import cn.qkmango.ccms.security.holder.AccountHolder;
import cn.qkmango.ccms.security.token.Jwt;
import cn.qkmango.ccms.security.token.TokenEntity;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    private ReloadableResourceBundleMessageSource ms;

    @Resource
    private AccountService service;

    @Resource
    private Jwt jwt;

    /**
     * 使用账户密码登陆
     *
     * @param account 用户对象
     * @return 登陆结果
     * @throws LoginException 登陆异常登陆失败
     */
    @PostMapping("system-login.do")
    public R<Object> systemLogin(Account account) throws LoginException {
        Account loginAccount = service.systemLogin(account);
        //创建 TokenEntity, 包含 token 和 过期时间
        TokenEntity tokenEntity = jwt.createEntity(loginAccount);
        return R.success(tokenEntity, ms.getMessage("response.login.success", null, LocaleContextHolder.getLocale()));
    }

    /**
     * 使用授权码登陆
     * <p>授权码为第三方认证后回调 {@link cn.qkmango.ccms.mvc.controller.AuthenticationController#callback(PlatformType, String, Map)}
     * 时返回的重定向URL中的授权码</p>
     *
     * @return
     */
    @PostMapping("access-login.do")
    public R accessLogin(@NotBlank String accessCode) throws LoginException {
        Account loginAccount = service.accessLogin(accessCode);
        //创建 TokenEntity, 包含 token 和 过期时间
        TokenEntity tokenEntity = jwt.createEntity(loginAccount);
        return R.success(tokenEntity, ms.getMessage("response.login.success", null, LocaleContextHolder.getLocale()));
    }


    /**
     * 修改密码
     * 修改当前登陆账户的密码
     *
     * @param dto 新的密码
     * @return 结果
     * @throws UpdateException 修改失败
     */

    @PostMapping("update/password.do")
    public R<Object> updatePassword(@Validated UpdatePasswordDto dto) throws UpdateException {
        Integer id = AccountHolder.getId();
        dto.setAccount(id);
        service.updatePassword(dto);
        return R.success(ms.getMessage("db.update.password.success", null, LocaleContextHolder.getLocale()));
    }


    /**
     * 重置密码
     *
     * @param account 账户 ID
     * @return 响应结果
     * @throws UpdateException 更新异常
     */
    @Permission(Role.admin)
    @PostMapping(value = "update/resetPassword.do")
    public R<Object> resetPassword(Integer account) throws UpdateException {
        service.resetPassword(account);
        return R.success(ms.getMessage("db.resetPassword.success", null, LocaleContextHolder.getLocale()));
    }


    /**
     * 注销账户
     *
     * @param account 账户
     * @return 响应结果
     * @throws UpdateException 更新异常
     */
    @Permission({Role.admin, Role.user})
    @PostMapping(value = "update/canceled.do")
    public R<Object> canceled(Integer account) throws UpdateException {
        service.canceled(account);
        return R.success(ms.getMessage("db.account.unsubscribe.success", null, LocaleContextHolder.getLocale()));
    }

    /**
     * 获取当前登陆账户信息
     *
     * @return 用户信息
     */
    @GetMapping("one/current-account-detail.do")
    public R currentAccountDetail() {
        Integer id = AccountHolder.getId();
        AccountDetailVO info = service.accountDetail(id);
        return R.success(info);
    }

    /**
     * 当前登陆用户基本信息
     *
     * @return 用户信息
     */
    @GetMapping("one/current-account-info.do")
    public R currentAccountInfo() {
        Integer id = AccountHolder.getId();
        Account account = service.getRecordById(id);
        return R.success(account);
    }

    /**
     * 获取用户账户详情
     *
     * @param account 账户ID
     */
    @Permission(Role.admin)
    @GetMapping("one/account-detail.do")
    public R accountDetail(Integer account) {
        AccountDetailVO info = service.accountDetail(account);
        if (info == null) {
            return R.fail(ms.getMessage("db.account.failure@notExist", null, LocaleContextHolder.getLocale()));
        }
        return R.success(info);
    }

    /**
     * 更新用户email
     *
     * @param email 新的email
     * @return 修改结果
     * @throws UpdateException 修改失败
     */
    @Permission({Role.admin, Role.user})
    @PostMapping("user/update/email.do")
    public R updateEmail(@NotBlank(message = "{valid.email.notBlank}") @Email(message = "{valid.email.illegal}") String email,
                         @Pattern(regexp = "^[a-zA-Z0-9]{5}$", message = "{valid.captcha.illegal}") String captcha) throws UpdateException {

        Integer id = AccountHolder.getId();
        Role role = AccountHolder.getRole();

        Account account = new Account().setId(id).setRole(role);
        service.updateEmail(account, email, captcha);
        return R.success(ms.getMessage("db.update.email.success", null, LocaleContextHolder.getLocale()));
    }

    /**
     * 账户分页查询
     *
     * @param pagination
     */
    @Permission(Role.admin)
    @PostMapping("pagination/list.do")
    public R<List<Account>> list(@RequestBody Pagination<Account> pagination) {
        return service.list(pagination);
    }

    @Permission(Role.admin)
    @PostMapping("one/insert.do")
    public R insert(@RequestBody @Validated AccountInsertDto account) throws InsertException {
        service.insert(account);
        return R.success(ms.getMessage("db.account.insert.success", null, LocaleContextHolder.getLocale()));
    }
}
