package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.util.EmailUtil;
import cn.qkmango.ccms.domain.bind.PermissionType;
import cn.qkmango.ccms.domain.entity.Account;
import jakarta.annotation.Resource;
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
@Permission(PermissionType.admin)
public class Test {

    @RequestMapping("/testLogin.do")
    public R<Account> test(HttpServletRequest request, PermissionType type) {
        HttpSession session = request.getSession(false);
        Account account;
        PermissionType p = PermissionType.admin;
        String id = "1";
        if (session == null) {
            account = new Account(id, null, "芒果小洛", p, null,null,null);
            request.getSession(true).setAttribute("account", account);
        } else {
            account = (Account) request.getSession().getAttribute("account");
            if (account == null) {
                account = new Account(id, null, "芒果小洛", p, null,null,null);
                request.getSession(true).setAttribute("account", account);
            }
        }
        return R.success(account);
    }
}
