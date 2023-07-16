package cn.qkmango.ccms.security.holder;

import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.security.token.JWT;
import jakarta.servlet.http.Cookie;
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

    private static final ThreadLocal<Map> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(Map account) {
        THREAD_LOCAL.set(account);
    }

    public static Map<String, Object> get() {
        return THREAD_LOCAL.get();
    }

    public static String getId() {
        Map<String, Object> map = get();
        if (map == null) {
            return null;
        }

        return (String) map.get("id");
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
        return account;
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

    /**
     * 向 response 中写入一个 cookie
     *
     * @param token      token
     * @param expireTime 过期时间, 单位: 秒<br>
     *                   推荐从 {@link JWT#getExpire()} 中获取, 以保证 token 和 cookie 的过期时间一致
     */
    public static void setTokenInCookie(String token, int expireTime) {
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(expireTime);
        cookie.setPath("/");

        HttpServletResponse response = getResponse();
        response.addCookie(cookie);
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
