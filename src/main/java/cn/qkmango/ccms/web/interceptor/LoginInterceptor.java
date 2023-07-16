package cn.qkmango.ccms.web.interceptor;

import cn.qkmango.ccms.common.util.ResponseUtil;
import cn.qkmango.ccms.security.holder.AccountHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * 登录拦截器
 * 判断用户是否登录，如果没有登录，则拦截请求
 *
 * @author qkmango
 */
public class LoginInterceptor implements HandlerInterceptor {

//    private final String LOGIN_URL;

//    public LoginInterceptor(String login) {
//        this.LOGIN_URL = login;
//    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
//        String path = request.getServletPath();

        //如果是登陆请求，则放行
//        if (LOGIN_URL.equals(path)) {
//            return true;
//        }

        //如果session存在，说明已经登陆过了，则放行
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            return true;
//        }

        Map account = AccountHolder.get();
        if (account != null) {
            return true;
        }

        response.setStatus(401);
        ResponseUtil.responseJson(response, "{\"success\":false,\"message\":\"未登录\"}");
        return false;

    }
}
