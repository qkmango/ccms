package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.Pos;
import cn.qkmango.ccms.domain.param.ChangePasswordParam;
import cn.qkmango.ccms.domain.vo.UserInfoVO;
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
    Account loginUser(Account account);

    /**
     * 修改密码
     *
     * @param param 新的密码
     * @return 数据库影响的行数
     */
    int updatePassword(ChangePasswordParam param);

    /**
     * 刷卡机登陆
     *
     * @param account 登陆账户
     * @return 已登陆的账户信息
     */
    Pos loginPos(Account account);

    /**
     * 管理员登陆
     *
     * @param account 登陆账户
     * @return 已登陆的账户信息
     */
    Account loginAdmin(Account account);

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
}
