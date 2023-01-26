package cn.qkmango.ccms.config;

import cn.qkmango.ccms.web.interceptor.LoginInterceptor;
import cn.qkmango.ccms.web.interceptor.PermissionsInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * 拦截器配置类
 * @author qkmango
 * @version 1.0
 * @date 2022-07-31 20:57
 */
// @Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    public void addInterceptors(InterceptorRegistry registry) {


        //国际化拦截器
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("locale");
        registry.addInterceptor(localeChangeInterceptor)
                .addPathPatterns("/system/setLocale.do");

        //登陆拦截器
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**/*.do")
                .excludePathPatterns("/account/login.do")
                .excludePathPatterns("/account/logout.do")
                .excludePathPatterns("/system/setLocale.do")
                .excludePathPatterns("/common/test.do")
                .excludePathPatterns("/test/**/*.do");


        PermissionsInterceptor permissionsInterceptor = new PermissionsInterceptor();
        registry.addInterceptor(permissionsInterceptor)
                .addPathPatterns("/**/*.do")
                .excludePathPatterns("/account/login.do")
                .excludePathPatterns("/account/logout.do")
                .excludePathPatterns("/system/setLocale.do")
                .excludePathPatterns("/common/test.do")
                .excludePathPatterns("/test/**/*.do")
                .excludePathPatterns("/common/test.do")
                .excludePathPatterns("/test/**/*.do");

    }

}
