package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.LoginException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.validate.group.Query;
import cn.qkmango.ccms.domain.bind.PermissionType;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.param.ChangePasswordParam;
import cn.qkmango.ccms.domain.vo.UserInfoVO;
import cn.qkmango.ccms.mvc.service.AccountService;
import cn.qkmango.ccms.mvc.service.UserService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Locale;

/**
 * 账户控制器
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 14:57
 */

@RestController
@RequestMapping("account")
public class AccountController {
    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private AccountService service;

    @Resource
    private UserService userService;

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

    @PostMapping("update/changePassword.do")
    public R<Object> changePassword(@Validated ChangePasswordParam param, HttpSession session, Locale locale) throws UpdateException {

        Account account = (Account) session.getAttribute("account");
        param.setPermissionType(account.getPermissionType());
        param.setId(account.getId());

        service.changePassword(param, locale);

        return R.success(messageSource.getMessage("db.changePassword.success", null, locale));
    }


    /**
     * 重置密码
     * 重置密码为身份证后6位
     *
     * @param card   校园卡
     * @param locale 语言环境
     * @return 响应结果
     * @throws UpdateException 更新异常
     */
    @Permission(PermissionType.admin)
    @PostMapping(value = "update/resetPassword.do")
    public R<Object> resetPassword(@Validated(Query.class) Card card, Locale locale) throws UpdateException {
        userService.resetPassword(card, locale);
        return R.success(messageSource.getMessage("db.resetPassword.success", null, locale));
    }


    /**
     * 注销账户
     *
     * @param card   校园卡
     * @param locale 语言环境
     * @return 响应结果
     * @throws UpdateException 更新异常
     */
    @Permission({PermissionType.admin, PermissionType.user})
    @PostMapping(value = "update/unsubscribe.do")
    public R<Object> unsubscribe(@Validated(Query.class) Card card, Locale locale) throws UpdateException {
        userService.unsubscribe(card, locale);
        return R.success(messageSource.getMessage("db.account.unsubscribe.success", null, locale));
    }

    /**
     * 获取用户信息
     *
     * @param session 会话
     * @return 用户信息
     */
    @Permission({PermissionType.admin, PermissionType.user})
    @GetMapping("one/user/info.do")
    public R<UserInfoVO> userInfo(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        String id = account.getId();
        UserInfoVO info = service.userInfo(id);
        return R.success(info);
    }

}