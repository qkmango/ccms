package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bind.AccountState;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    @RequestMapping("/testLogin.do")
    public R<Account> test(HttpServletRequest request, Role type) {
        HttpSession session = request.getSession(false);
        Account account;
        Role p = Role.admin;
        String id = "1";
        if (session == null) {
            account = new Account(id, null, Role.admin, AccountState.normal,1);
            request.getSession(true).setAttribute("account", account);
        } else {
            account = (Account) request.getSession().getAttribute("account");
            if (account == null) {
                account = new Account(id, null, Role.admin, AccountState.normal,1);
                request.getSession(true).setAttribute("account", account);
            }
        }
        return R.success(account);
    }
}
