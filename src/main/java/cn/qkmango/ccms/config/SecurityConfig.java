package cn.qkmango.ccms.config;

import cn.qkmango.ccms.security.client.AlipayAuthHttpClient;
import cn.qkmango.ccms.security.client.AuthHttpClient;
import cn.qkmango.ccms.security.client.DingtalkAuthHttpClient;
import cn.qkmango.ccms.security.client.GiteeAuthHttpClient;
import cn.qkmango.ccms.security.config.AlipayAppConfig;
import cn.qkmango.ccms.security.config.AppConfig;
import cn.qkmango.ccms.security.encoder.BCryptPasswordEncoder;
import cn.qkmango.ccms.security.encoder.PasswordEncoder;
import cn.qkmango.ccms.security.token.Jwt;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Bean(name = "jwt")
    @ConfigurationProperties(prefix = "ccms.jwt")
    public Jwt jwt() {
        return new Jwt();
    }

    // 密码加密器
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ============================= 第三方授权登陆 =============================
    // Gitee 第三方平台配置
    @Bean("giteeAuthConfig")
    @ConfigurationProperties(prefix = "ccms.authentication.gitee")
    public AppConfig giteeAuthConfig() {
        return new AppConfig();
    }

    // Dingtalk 第三方平台配置
    @Bean("dingtalkAuthConfig")
    @ConfigurationProperties(prefix = "ccms.authentication.dingtalk")
    public AppConfig dingtalkAuthConfig() {
        return new AppConfig();
    }

    // Alipay 第三方平台配置
    @Bean("alipayAuthConfig")
    @ConfigurationProperties(prefix = "ccms.authentication.alipay")
    public AlipayAppConfig alipayAuthConfig() {
        return new AlipayAppConfig();
    }

    // Gitee 第三方平台授权登陆客户端
    @Bean("giteeAuthHttpClient")
    public AuthHttpClient giteeHttpRequest(@Qualifier("giteeAuthConfig") AppConfig config,
                                           ReloadableResourceBundleMessageSource ms) {
        return new GiteeAuthHttpClient(config, ms);
    }

    // Dingtalk 第三方平台授权登陆客户端
    @Bean("dingtalkAuthHttpClient")
    public AuthHttpClient dingtalkHttpRequest(@Qualifier("dingtalkAuthConfig") AppConfig config,
                                              ReloadableResourceBundleMessageSource ms) {
        return new DingtalkAuthHttpClient(config, ms);
    }

    // Alipay 第三方平台授权登陆客户端
    @Bean("alipayAuthHttpClient")
    public AuthHttpClient alipayHttpRequest(@Qualifier("alipayAuthConfig") AlipayAppConfig config,
                                            ReloadableResourceBundleMessageSource ms) {
        return new AlipayAuthHttpClient(config, ms);
    }
}
