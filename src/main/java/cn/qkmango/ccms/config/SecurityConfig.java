package cn.qkmango.ccms.config;

import cn.qkmango.ccms.common.util.RedisUtil;
import cn.qkmango.ccms.security.cache.DefaultSecurityCache;
import cn.qkmango.ccms.security.cache.SecurityCache;
import cn.qkmango.ccms.security.client.AlipayAuthHttpClient;
import cn.qkmango.ccms.security.client.AuthHttpClient;
import cn.qkmango.ccms.security.client.DingtalkAuthHttpClient;
import cn.qkmango.ccms.security.client.GiteeAuthHttpClient;
import cn.qkmango.ccms.security.config.AlipayAppConfig;
import cn.qkmango.ccms.security.config.AppConfig;
import cn.qkmango.ccms.security.encoder.BCryptPasswordEncoder;
import cn.qkmango.ccms.security.encoder.PasswordEncoder;
import cn.qkmango.ccms.security.token.Jwt;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
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
    @Bean("authStateCache")
    public SecurityCache authStateCache() {
        return new DefaultSecurityCache("auth:state:", redisUtil, 60 * 5);
    }

    @Bean("authAccessCodeCache")
    public SecurityCache authAccessCodeCache() {
        return new DefaultSecurityCache("auth:code:", redisUtil, 60 * 5);
    }

    @Value("${ccms.jwt.secret}")
    private String secret;
    @Value("${ccms.jwt.expire}")
    private int expire;

    @Bean(name = "jwt")
    public Jwt jwt() {
        return new Jwt(secret, expire);
    }

    // 密码加密器
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    // Gitee 第三方平台配置
    @Bean("giteeAuthConfig")
    @ConfigurationProperties(prefix = "ccms.authentication.gitee")
    public AppConfig giteeConfig() {
        return new AppConfig();
    }

    // Dingtalk 第三方平台配置
    @Bean("dingtalkAuthConfig")
    @ConfigurationProperties(prefix = "ccms.authentication.dingtalk")
    public AppConfig dingtalkConfig() {
        return new AppConfig();
    }

    // Alipay 第三方平台配置
    @Bean("alipayAuthConfig")
    @ConfigurationProperties(prefix = "ccms.authentication.alipay")
    public AlipayAppConfig alipayConfig() {
        return new AlipayAppConfig();
    }

    // Gitee 第三方平台授权登陆客户端
    @Bean("giteeAuthHttpClient")
    public AuthHttpClient giteeHttpRequest() {
        return new GiteeAuthHttpClient(giteeConfig(), messageSource);
    }

    // Dingtalk 第三方平台授权登陆客户端
    @Bean("dingtalkAuthHttpClient")
    public AuthHttpClient dingtalkHttpRequest() {
        return new DingtalkAuthHttpClient(dingtalkConfig(), messageSource);
    }

    @Bean("alipayAuthHttpClient")
    public AuthHttpClient alipayHttpRequest() {
        return new AlipayAuthHttpClient(alipayConfig(), messageSource);
    }
}
