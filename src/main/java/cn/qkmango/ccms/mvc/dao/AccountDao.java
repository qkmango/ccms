package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.pagination.Pagination;
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
public interface AccountDao extends BaseDao<Account, String> {

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
     * 账户详情
     *
     * @param id
     * @return
     */
    Account getRecordById(Integer id);

    Account getRecordById(Integer id, Boolean password);


    /**
     * 更新账户信息
     *
     * @param updateAccount 更新的账户信息
     * @return 数据库影响的行数
     */
    int update(Account updateAccount);

    /**
     * 登陆
     *
     * @param account
     * @return
     */
    Account login(Account account);

    List<Account> list(Pagination<Account> pagination);

    int insert(Account account);
}
