package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.bind.AccountState;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;

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
     * @param id    账户ID
     * @param email 新的email
     * @return 数据库影响的行数
     */
    int updateEmail(Integer id, String email);


    /**
     * 账户详情
     * 包含 password
     *
     * @param id
     * @return
     */
    Account getRecordById(Integer id);

    /**
     * 账户详情
     *
     * @param id
     * @param password 是否查询 password
     */
    Account getRecordById(Integer id, Boolean password);

    /**
     * 更新账户信息
     *
     * @param updateAccount 更新的账户信息
     * @return 数据库影响的行数
     */
    int update(Account updateAccount);

    List<Account> list(Pagination<Account> pagination);

    @Override
    int insert(Account account);

    List<Integer> accountIdsByDepartment(Integer department);

    /**
     * 修改状态
     *
     * @param id
     * @param state
     * @param version
     * @return
     */
    int state(Integer id, AccountState state, Integer version);
}
