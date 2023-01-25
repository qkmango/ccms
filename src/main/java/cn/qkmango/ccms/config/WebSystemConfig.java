package cn.qkmango.ccms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 * @author qkmango
 * @version 1.0
 * @date 2022-07-31 19:26
 */

@Configuration
public class WebSystemConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/page/login").setViewName("forward:/page/login/index.html");
    }
}
