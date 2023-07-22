package cn.qkmango.ccms.web.interceptor;

import cn.qkmango.ccms.security.holder.AccountHolder;
import cn.qkmango.ccms.security.token.Jwt;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
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
@Component("tokenInterceptor")
public class TokenInterceptor implements HandlerInterceptor {

    @Resource(name = "jwt")
    private Jwt jwt;

    public TokenInterceptor(Jwt jwt) {
        this.jwt = jwt;
    }

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）解析 token 并将用户信息存入 UserSession
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        Claims claims = jwt.parser(token);

        //将解析的用户信息 Map<String,Object> claims 存入 UserSession
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
