package cn.qkmango.ccms.common.util;

import cn.qkmango.ccms.domain.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 用户会话工具类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-29 17:12
 */
public class UserSession {

    /**
     * 获取当前请求的session
     *
     * @return session
     */
    public static HttpSession getSession() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
        return request.getSession(false);
    }

    /**
     * 获取当前请求的用户账户
     *
     * @return account 用户账户
     */
    public static Account getAccount() {
        HttpSession session = getSession();
        if (session == null) {
            return null;
        }
        return (Account) session.getAttribute("account");
    }
}
