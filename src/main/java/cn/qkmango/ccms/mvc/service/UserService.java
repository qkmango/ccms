package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.domain.entity.User;

import java.util.List;

/**
 * 学生信息服务层接口
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-28 22:04
 */
public interface UserService {

    List<User> departmentUserList(Integer department);
}
