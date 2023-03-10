package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.LoginException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.User;
import cn.qkmango.ccms.domain.param.ChangePasswordParam;
import cn.qkmango.ccms.domain.vo.UserInfoVO;

import java.util.List;
import java.util.Locale;

/**
 * 账户
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 14:58
 */
public interface AccountService {
    /**
     * 登陆接口
     *
     * @param account 账户
     * @param locale  语言环境
     * @return 登陆成功返回登陆用户信息
     * @throws LoginException 登陆异常
     */
    Account login(Account account, Locale locale) throws LoginException;

    /**
     * 修改密码
     *
     * @param param  新的密码和
     * @param locale 语言环境
     * @throws UpdateException 更新异常
     */
    void updatePassword(ChangePasswordParam param, Locale locale) throws UpdateException;

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserInfoVO userInfo(String id);

    /**
     * 更新用户email
     *
     * @param account 账户
     * @param captcha 验证码
     * @param email   新的email
     * @param locale  语言环境
     * @throws UpdateException 更新异常
     */
    void updateEmail(Account account, String email, String captcha, Locale locale) throws UpdateException;

    /**
     * 同班同学列表
     *
     * @return 同班同学列表
     */
    List<Account> clazzmate();
}
