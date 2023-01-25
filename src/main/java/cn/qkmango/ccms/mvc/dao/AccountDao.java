package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.Pos;
import cn.qkmango.ccms.domain.param.ChangePasswordParam;
import cn.qkmango.ccms.domain.vo.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;

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
    Account login(Account account);

    /**
     * 修改密码
     *
     * @param param 新的密码
     * @return 数据库影响的行数
     */
    int changePassword(ChangePasswordParam param);

    /**
     * 刷卡机登陆
     *
     * @param account 登陆账户
     * @return 已登陆的账户信息
     */
    Pos loginPos(Account account);

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserInfoVO userInfo(String id);
}
