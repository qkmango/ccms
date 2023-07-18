package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.common.exception.permission.LoginException;
import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.AccountInsertParam;
import cn.qkmango.ccms.domain.param.UpdatePasswordParam;
import cn.qkmango.ccms.domain.vo.AccountInfoVO;

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
    void updatePassword(UpdatePasswordParam param, Locale locale) throws UpdateException;

    /**
     * 获取用户信息
     *
     * @param accountId 用户ID
     * @return 用户信息
     */
    AccountInfoVO accountInfo(String accountId);

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
     * 注销账户
     *
     * @param account 账户 ID
     * @param locale  语言环境
     */
    void canceled(String account, Locale locale) throws UpdateException;

    /**
     * 重置密码
     *
     * @param account 账户ID
     */
    void resetPassword(String account, Locale locale) throws UpdateException;

    /**
     * 账户分页查询
     *
     * @param pagination
     * @return
     */
    R<List<Account>> list(Pagination<Account> pagination);

    /**
     * 添加账户
     *
     * @param account
     * @param locale
     */
    void insert(AccountInsertParam account, Locale locale) throws InsertException;

}
