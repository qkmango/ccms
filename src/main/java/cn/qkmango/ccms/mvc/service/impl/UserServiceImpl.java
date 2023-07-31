package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.domain.entity.User;
import cn.qkmango.ccms.mvc.dao.AccountDao;
import cn.qkmango.ccms.mvc.dao.UserDao;
import cn.qkmango.ccms.mvc.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户业务层实现类
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-28 22:04
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;
    @Resource
    private AccountDao accountDao;

    @Override
    public List<User> departmentUserList(Integer department) {
        //先查出该部门的所有 account id
        List<Integer> ids = accountDao.accountIdsByDepartment(department);
        //再根据 account id 查出所有 user
        List<User> users = userDao.usersByAccountIds(ids);
        return users;
    }
}
