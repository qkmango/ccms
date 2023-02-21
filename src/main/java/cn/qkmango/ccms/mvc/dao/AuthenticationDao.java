package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.dto.Authentication;
import cn.qkmango.ccms.domain.entity.Account;
import org.apache.ibatis.annotations.Mapper;

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
     *                       <li>用户权限 permission
     *                       <li>认证类型 Type
     *                       <li>认证用途 purpose
     *                       <li>三方平台返回的用户唯一标识 uid
     * @return 返回账户信息
     */
    Account authentication(Authentication authentication);

}
