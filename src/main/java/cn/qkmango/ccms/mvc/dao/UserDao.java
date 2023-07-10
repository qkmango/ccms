package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生 Dao 层接口
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 20:34
 */
@Mapper
public interface UserDao {

    User getUser(String id);

    int insert(User user);

    int resetPassword(User user);

    // int unsubscribe(Card card);

    boolean isUnsubscribe(String id);

    /**
     * 符合条件的用户数量，条件为或关系
     *
     * @param user 用户查询条件
     * @return 符合条件的用户数量
     */
    int userCountOr(User user);

    /**
     * 获取指定班级的user id
     *
     * @param clazzIds 班级id数组
     * @return user id
     */
    List<String> getUserIdByClazzIds(@Param("clazzIds") List<String> clazzIds);

    /**
     * 获取用户详情
     *
     * @param account
     * @return
     */
    User getRecordByAccount(String account);
}
