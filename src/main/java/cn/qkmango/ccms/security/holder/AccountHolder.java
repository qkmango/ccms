package cn.qkmango.ccms.security.holder;

import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

/**
 * 用户会话工具类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-29 17:12
 */
public class AccountHolder {

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(Map<String, Object> account) {
        THREAD_LOCAL.set(account);
    }

    public static Map<String, Object> get() {
        return THREAD_LOCAL.get();
    }

    public static Integer getId() {
        Map<String, Object> map = get();
        if (map == null) {
            return null;
        }

        return (Integer) map.get("id");
    }

    public static Role getRole() {
        Map<String, Object> map = get();
        if (map == null) {
            return null;
        }
        return Role.valueOf((String) map.get("role"));
    }

    public static Account getAccount() {
        if (get() == null) {
            return null;
        }
        Account account = new Account();
        account.setId(getId());
        account.setRole(getRole());
        account.setDepartment((Integer) get().get("department"));
        return account;
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

    public static HttpServletRequest getRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        return ((ServletRequestAttributes) attributes).getRequest();
    }

    public static HttpServletResponse getResponse() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        return ((ServletRequestAttributes) attributes).getResponse();
    }

}
