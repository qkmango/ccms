package cn.qkmango.ccms.config;

import cn.qkmango.ccms.security.token.Jwt;
import cn.qkmango.ccms.web.interceptor.TokenInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-07-31 20:57
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource(name = "jwt")
    private Jwt jwt;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // JWT 拦截器, 用于验证 token, 并将用户信息放入 ThreadLocal
        TokenInterceptor tokenInterceptor = new TokenInterceptor(jwt);
        registry.addInterceptor(tokenInterceptor)
                .excludePathPatterns("/account/system-login.do")
                .excludePathPatterns("/account/access-login.do");

        //登陆拦截器
//        LoginInterceptor loginInterceptor = new LoginInterceptor();
//        registry.addInterceptor(loginInterceptor)
//                .excludePathPatterns("/account/system-login.do")
//                .excludePathPatterns("/account/access-login.do");


        // PermissionsInterceptor permissionsInterceptor = new PermissionsInterceptor();
        // registry.addInterceptor(permissionsInterceptor)
        //         .addPathPatterns("/**/*.do")
        //         .excludePathPatterns("/account/login.do")
        //         .excludePathPatterns("/account/logout.do")
        //         .excludePathPatterns("/system/setLocale.do")
        //         .excludePathPatterns("/common/test.do")
        //         .excludePathPatterns("/test/**/*.do")
        //         .excludePathPatterns("/common/test.do")
        //         .excludePathPatterns("/test/**/*.do");

    }

}
