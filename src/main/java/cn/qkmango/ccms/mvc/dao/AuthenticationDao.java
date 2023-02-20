package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.dto.Authentication;
import cn.qkmango.ccms.domain.entity.Account;
import org.apache.ibatis.annotations.Mapper;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-18 19:26
 */
@Mapper
public interface AuthenticationDao {

    Account authentication(Authentication authentication);

}
