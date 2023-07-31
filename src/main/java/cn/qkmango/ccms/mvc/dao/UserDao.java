package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 学生 Dao 层接口
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 20:34
 */
@Mapper
public interface UserDao extends BaseDao<User, Long> {

    int insert(User user);

    /**
     * 获取用户详情
     *
     * @param account
     * @return
     */
    User getRecordByAccount(Integer account);

    List<User> usersByAccountIds(List<Integer> ids);
}
