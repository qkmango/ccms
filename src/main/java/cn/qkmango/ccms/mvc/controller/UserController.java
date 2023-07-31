package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.User;
import cn.qkmango.ccms.mvc.service.UserService;
import cn.qkmango.ccms.security.holder.AccountHolder;
import jakarta.annotation.Resource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-31 16:32
 */
@Validated
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private ReloadableResourceBundleMessageSource ms;

    @Resource
    private UserService service;

    /**
     * 获取当前用户所在部门的所有用户
     */
    @GetMapping("list/department-user-list.do")
    public R<List<User>> departmentUserList() {
        Integer department = AccountHolder.getAccount().getDepartment();
        List<User> list = service.departmentUserList(department);
        return R.success(list);
    }
}
