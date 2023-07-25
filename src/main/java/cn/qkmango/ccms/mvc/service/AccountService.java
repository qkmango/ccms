package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.exception.permission.LoginException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.dto.AccountInsertDto;
import cn.qkmango.ccms.domain.dto.UpdatePasswordDto;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.vo.AccountDetailVO;

import java.util.List;

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
     * @return 登陆成功返回登陆用户信息
     * @throws LoginException 登陆异常
     */
    Account systemLogin(Account account) throws LoginException;

    /**
     * 修改密码
     *
     * @param dto  新的密码和
     * @throws UpdateException 更新异常
     */
    void updatePassword(UpdatePasswordDto dto) throws UpdateException;

    /**
     * 获取用户信息
     *
     * @param accountId 用户ID
     * @return 用户信息
     */
    AccountDetailVO accountDetail(Integer accountId);

    /**
     * 更新用户email
     *
     * @param account 账户
     * @param captcha 验证码
     * @param email   新的email
     * @throws UpdateException 更新异常
     */
    void updateEmail(Account account, String email, String captcha) throws UpdateException;


    /**
     * 注销账户
     *
     * @param account 账户 ID
     */
    void canceled(Integer account) throws UpdateException;

    /**
     * 重置密码
     *
     * @param account 账户ID
     */
    void resetPassword(Integer account) throws UpdateException;

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
     */
    void insert(AccountInsertDto account) throws InsertException;

    Account getRecordById(Integer id);

    Account accessLogin(String accessCode) throws LoginException;

}
