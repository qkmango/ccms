package cn.qkmango.ccms.config;

import cn.qkmango.ccms.common.security.BCryptPasswordEncoder;
import cn.qkmango.ccms.common.security.PasswordEncoder;
import cn.qkmango.ccms.common.util.RedisUtil;
import cn.qkmango.ccms.security.cache.DefaultStateCache;
import cn.qkmango.ccms.security.cache.StateCache;
import cn.qkmango.ccms.security.client.AuthHttpClient;
import cn.qkmango.ccms.security.client.DingtalkAuthHttpClient;
import cn.qkmango.ccms.security.client.GiteeAuthHttpClient;
import cn.qkmango.ccms.security.config.AppConfig;
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

    @Bean("giteeConfig")
    @ConfigurationProperties(prefix = "ccms.authentication.gitee")
    public AppConfig giteeConfig() {
        return new AppConfig();
    }

    @Bean("dingtalkConfig")
    @ConfigurationProperties(prefix = "ccms.authentication.dingtalk")
    public AppConfig dingtalkConfig() {
        return new AppConfig();
    }

    // Gitee 第三方平台授权登陆客户端
    @Bean("giteeAuthHttpClient")
    public AuthHttpClient giteeHttpRequest() {
        AppConfig config = new AppConfig()
                .setId("06c9956bdfd279c5c0a81f187711cc7137f268331f701fcf53382e40e9f69cee")
                .setSecret("adc73ff714c0b29d0af634c2d0f20187d089eb7fdbfc7809a26afa85932010e1")
                .add("authorize", "https://gitee.com/oauth/authorize")
                .add("accessToken", "https://gitee.com/oauth/token")
                .add("callbackBind", "http://localhost/authentication/gitee/bind.do")
                .add("callbackLogin", "http://localhost/authentication/gitee/login.do")
                .add("userInfo", "https://gitee.com/api/v5/user")
                .add("redirect", "redirect:/page/common/authentication/result.html");

        return new GiteeAuthHttpClient(config, stateCache(), messageSource);
    }

    // Dingtalk 第三方平台授权登陆客户端
    @Bean("dingtalkAuthHttpClient")
    public AuthHttpClient dingtalkHttpRequest() {
        AppConfig config = new AppConfig()
                .setId("dingfi9mcuubqqqkgbec")
                .setSecret("2EF6rPtFpWN3dgMKIqihFGknDCbE6ceGKk2RmBfpP5hTvdcsFZGz9N6uqyvItWd7")
                .add("authorize", "https://login.dingtalk.com/oauth2/auth")
                .add("accessToken", "https://oapi.dingtalk.com/gettoken")
                .add("callbackBind", "http://localhost/authentication/dingtalk/bind.do")
                .add("callbackLogin", "http://localhost/authentication/dingtalk/login.do")
                .add("userInfo", "https://oapi.dingtalk.com/sns/getuserinfo_bycode")
                .add("redirect", "redirect:/page/common/authentication/result.html");

        return new DingtalkAuthHttpClient(config, stateCache(), messageSource);
    }
}
