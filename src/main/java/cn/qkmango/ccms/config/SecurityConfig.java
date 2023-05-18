package cn.qkmango.ccms.config;

import cn.qkmango.ccms.common.security.BCryptPasswordEncoder;
import cn.qkmango.ccms.common.security.PasswordEncoder;
import cn.qkmango.ccms.security.HttpRequestBaseURL;
import cn.qkmango.ccms.security.request.*;
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

    @Bean("giteeHttpRequest")
    public HttpRequest giteeHttpRequest() {
        ClientIdAndSecret clientIdAndSecret = new ClientIdAndSecret();
        clientIdAndSecret.setId("06c9956bdfd279c5c0a81f187711cc7137f268331f701fcf53382e40e9f69cee");
        clientIdAndSecret.setSecret("adc73ff714c0b29d0af634c2d0f20187d089eb7fdbfc7809a26afa85932010e1");

        HttpRequestBaseURL httpRequestBaseURL = new HttpRequestBaseURL();
        httpRequestBaseURL.setAuthorizeURL(new RequestURL("https://gitee.com/oauth/authorize"));
        httpRequestBaseURL.setAccessTokenURL(new RequestURL("https://gitee.com/oauth/token"));
        httpRequestBaseURL.setCallbackBindURL(new RequestURL("http://localhost/authentication/gitee/bind.do"));
        httpRequestBaseURL.setCallbackLoginURL(new RequestURL("http://localhost/authentication/gitee/login.do"));
        httpRequestBaseURL.setUserInfoURL(new RequestURL("https://gitee.com/api/v5/user"));

        GiteeHttpRequest giteeHttpRequest = new GiteeHttpRequest();
        giteeHttpRequest.setClient(clientIdAndSecret);
        giteeHttpRequest.setHttpRequestBaseUrl(httpRequestBaseURL);

        return giteeHttpRequest;
    }

    @Bean("dingtalkHttpRequest")
    public HttpRequest dingtalkHttpRequest() {
        ClientIdAndSecret clientIdAndSecret = new ClientIdAndSecret();
        clientIdAndSecret.setId("dingfi9mcuubqqqkgbec");
        clientIdAndSecret.setSecret("2EF6rPtFpWN3dgMKIqihFGknDCbE6ceGKk2RmBfpP5hTvdcsFZGz9N6uqyvItWd7");

        HttpRequestBaseURL httpRequestBaseURL = new HttpRequestBaseURL();
        httpRequestBaseURL.setAuthorizeURL(new RequestURL("https://login.dingtalk.com/oauth2/auth"));
        httpRequestBaseURL.setAccessTokenURL(new RequestURL("https://oapi.dingtalk.com/gettoken"));
        httpRequestBaseURL.setCallbackBindURL(new RequestURL("http://localhost/authentication/dingtalk/bind.do"));
        httpRequestBaseURL.setCallbackLoginURL(new RequestURL("http://localhost/authentication/dingtalk/login.do"));
        httpRequestBaseURL.setUserInfoURL(new RequestURL("https://oapi.dingtalk.com/sns/getuserinfo_bycode"));

        DingtalkHttpRequest dingtalkHttpRequest = new DingtalkHttpRequest();
        dingtalkHttpRequest.setClient(clientIdAndSecret);
        dingtalkHttpRequest.setHttpRequestBaseUrl(httpRequestBaseURL);

        return dingtalkHttpRequest;
    }
}
