package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bind.AccountState;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.security.holder.AccountHolder;
import cn.qkmango.ccms.security.token.Jwt;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 18:16
 */

@RestController
@RequestMapping("test")
@Permission(Role.admin)
public class Test {

    @Resource
    private Jwt jwt;

    @RequestMapping("/testLogin.do")
    public R<Account> test(HttpServletRequest request, Role type) {
        Account account = AccountHolder.getAccount();

        if (account == null) {
            account = new Account(1, null, Role.admin, AccountState.normal, 1);
        }
        return R.success(account);
    }
}
