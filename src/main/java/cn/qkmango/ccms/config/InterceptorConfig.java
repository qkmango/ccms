package cn.qkmango.ccms.config;

import cn.qkmango.ccms.web.interceptor.LoginInterceptor;
import cn.qkmango.ccms.web.interceptor.PermissionsInterceptor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${ccms.login.api}")
    private String loginApi;

    @Value("${ccms.response.NO_LOGIN_JSON}")
    private String NO_LOGIN_JSON;

    @Value("${ccms.response.OPERATION_WITHOUT_PERMISSION_JSON}")
    private String OPERATION_WITHOUT_PERMISSION_JSON;

    public void addInterceptors(InterceptorRegistry registry) {


        //国际化拦截器
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("locale");
        registry.addInterceptor(localeChangeInterceptor)
                .addPathPatterns("/system/setLocale.do");

        //登陆拦截器
        LoginInterceptor loginInterceptor = new LoginInterceptor(loginApi,NO_LOGIN_JSON);
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**/*.do")
                .excludePathPatterns("/account/login.do")
                .excludePathPatterns("/account/logout.do")
                .excludePathPatterns("/system/setLocale.do")
                .excludePathPatterns("/common/test.do")
                .excludePathPatterns("/test/**/*.do");


        PermissionsInterceptor permissionsInterceptor = new PermissionsInterceptor(loginApi,OPERATION_WITHOUT_PERMISSION_JSON);
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
