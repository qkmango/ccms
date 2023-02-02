package cn.qkmango.ccms.web.interceptor;

import cn.qkmango.ccms.common.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器
 * 判断用户是否登录，如果没有登录，则拦截请求
 *
 * @author qkmango
 */
public class LoginInterceptor implements HandlerInterceptor {

    private String LOGIN_API;
    private String NO_LOGIN_JSON;

    public LoginInterceptor(String loginApi, String NO_LOGIN_JSON) {
        this.LOGIN_API = loginApi;
        this.NO_LOGIN_JSON = NO_LOGIN_JSON;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String path = request.getServletPath();

        //如果是登陆请求，则放行
        if (LOGIN_API.equals(path)) {
            return true;
        }

        //如果session存在，说明已经登陆过了，则放行
        HttpSession session = request.getSession(false);
        if (session != null) {
            return true;
        }

        response.setStatus(401);
        ResponseUtil.responseJson(response, NO_LOGIN_JSON);
        return false;

    }
}
