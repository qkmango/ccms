package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.Pos;
import cn.qkmango.ccms.domain.vo.AccountInfoVO;
import cn.qkmango.ccms.domain.vo.UserAccountInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 17:49
 */
@Mapper
public interface AccountDao {

    /**
     * 登陆
     *
     * @param account 登陆账户
     * @return 已登陆的账户信息
     */
//    Account loginUser(Account account);

    /**
     * 获取账户密码
     *
     * @param id 账户id
     * @return 账户密码
     */
    String getAccountPassword(String id, Role type);

    /**
     * 修改密码
     *
     * @return 数据库影响的行数
     */
    int updatePassword(String id, String password, Role type);

    /**
     * 刷卡机登陆
     *
     * @param account 登陆账户
     * @return 已登陆的账户信息
     */
//    Pos loginPos(Account account);

    /**
     * 管理员登陆
     *
     * @param account 登陆账户
     * @return 已登陆的账户信息
     */
//    Account loginAdmin(Account account);

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserAccountInfoVO userAccountInfo(String id);


    /**
     * 获取管理员信息
     *
     * @param id 管理员ID
     * @return 管理员信息
     */
    AccountInfoVO adminAccountInfo(String id);

    /**
     * 更新用户email
     *
     * @param account 账户
     * @param email   新的email
     * @return 数据库影响的行数
     */
    int updateEmail(@Param("account") Account account,
                    @Param("email") String email);

    /**
     * 同组用户列表
     *
     * @return 同组用户列表
     */
    List<Account> groupUser(String id);


    /**
     * 账户详情
     *
     * @param id
     * @return
     */
    Account getRecordById(String id);


    /**
     * 更新账户信息
     *
     * @param updateAccount 更新的账户信息
     * @return 数据库影响的行数
     */
    int update(Account updateAccount);

    /**
     * 登陆
     * @param account
     * @return
     */
    Account login(Account account);
}
