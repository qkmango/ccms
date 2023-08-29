package cn.qkmango.ccms.web.interceptor;

import cn.qkmango.ccms.security.holder.AccountHolder;
import cn.qkmango.ccms.security.token.Jwt;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * token 拦截器
 * <p>用于从请求头中获取token, 并将其添加到 </p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-16 13:42
 */
public class TokenInterceptor implements HandlerInterceptor {

    private final Jwt jwt;
    private static final String TOKEN_NAME = "Authorization";

    public TokenInterceptor(Jwt jwt) {
        this.jwt = jwt;
    }

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）解析 token 并将用户信息存入 AccountHolder
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = null;

        // 从请求头中获取 token
        token = request.getHeader(TOKEN_NAME);
        if (token == null) {
            // 从 cookie 中获取 token
            Cookie[] cookies = request.getCookies();
            for (int i = 0; cookies != null && i < cookies.length; i++) {
                if (TOKEN_NAME.equals(cookies[i].getName())) {
                    token = cookies[i].getValue();
                    break;
                }
            }
        }

        // 将解析的用户信息 Map<String,Object> claims 存入 AccountHolder
        Claims claims = jwt.parser(token);
        if (claims != null) {
            AccountHolder.set(claims);
        }

        return true;
    }

    /**
     * 在请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）移除 UserSession 中的用户信息
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        AccountHolder.remove();
    }
}
