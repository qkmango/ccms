package cn.qkmango.ccms.config;

import cn.qkmango.ccms.common.security.BCryptPasswordEncoder;
import cn.qkmango.ccms.common.security.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 安全配置类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-12 16:26
 */
@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder BCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
