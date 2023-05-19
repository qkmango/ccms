package cn.qkmango.ccms.config;

import cn.qkmango.ccms.common.util.RedisUtil;
import cn.qkmango.ccms.security.cache.DefaultStateCache;
import cn.qkmango.ccms.security.cache.StateCache;
import cn.qkmango.ccms.security.client.AuthHttpClient;
import cn.qkmango.ccms.security.client.DingtalkAuthHttpClient;
import cn.qkmango.ccms.security.client.GiteeAuthHttpClient;
import cn.qkmango.ccms.security.config.AppConfig;
import cn.qkmango.ccms.security.encoder.BCryptPasswordEncoder;
import cn.qkmango.ccms.security.encoder.PasswordEncoder;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 安全配置类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-12 16:26
 */
@Configuration
public class SecurityConfig {

    // redis工具类
    @Resource(name = "redisUtil")
    private RedisUtil redisUtil;

    // 认证 state 缓存工具
    @Bean
    public StateCache stateCache() {
        return new DefaultStateCache(redisUtil, 60 * 5);
    }

    // 密码加密器
    @Bean
    public PasswordEncoder BCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    /**
     * Gitee 第三方平台配置
     *
     * @return 配置
     */
    @Bean("giteeConfig")
    @ConfigurationProperties(prefix = "ccms.authentication.gitee")
    public AppConfig giteeConfig() {
        return new AppConfig();
    }

    /**
     * Dingtalk 第三方平台配置
     *
     * @return 配置
     */
    @Bean("dingtalkConfig")
    @ConfigurationProperties(prefix = "ccms.authentication.dingtalk")
    public AppConfig dingtalkConfig() {
        return new AppConfig();
    }

    // Gitee 第三方平台授权登陆客户端
    @Bean("giteeAuthHttpClient")
    public AuthHttpClient giteeHttpRequest() {
        return new GiteeAuthHttpClient(giteeConfig(), stateCache(), messageSource);
    }

    // Dingtalk 第三方平台授权登陆客户端
    @Bean("dingtalkAuthHttpClient")
    public AuthHttpClient dingtalkHttpRequest() {
        return new DingtalkAuthHttpClient(dingtalkConfig(), stateCache(), messageSource);
    }
}
