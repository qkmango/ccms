package cn.qkmango.ccms.config;

import cn.qkmango.ccms.config.converter.DateConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
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
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateConverter());
    }
}
