package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.OpenPlatform;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 认证信息持久层接口
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-18 19:26
 */
@Mapper
public interface AuthenticationDao {

    /**
     * 根据授权信息查询账户信息
     *
     * @param authentication 授权信息
     *                       <li>用户角色 role
     *                       <li>认证类型 Type
     *                       <li>认证用途 purpose
     *                       <li>三方平台返回的用户唯一标识 uid
     * @return 返回账户信息
     */
    // Account authentication(AuthenticationAccount authentication);

    /**
     * 获取开放平台绑定状态
     *
     * @param account 系统账户
     * @return 返回开放平台绑定状态
     */
    List<OpenPlatform> openPlatformBindState(Account account);

    /**
     * 绑定第三方平台
     *
     * @param platform 开放平台
     * @param account      已登陆的账户
     * @return 数据库影响的行数
     */
    int toBind(@Param("platform") OpenPlatform platform, @Param("account") Account account);

    /**
     * 解绑开放平台
     *
     * @param platform 平台类型
     * @param account  已登陆的账户
     * @return 数据库影响的行数
     */
    int unbind(@Param("platform") PlatformType platform, @Param("account") Account account);

    /**
     * 判断账户指定平台是否绑定
     */
    boolean isBind(OpenPlatform platform, Account account);
}
