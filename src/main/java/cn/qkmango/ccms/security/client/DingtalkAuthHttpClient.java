package cn.qkmango.ccms.security.client;

import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.auth.PurposeType;
import cn.qkmango.ccms.security.AuthenticationResult;
import cn.qkmango.ccms.security.UserInfo;
import cn.qkmango.ccms.security.config.AppConfig;
import cn.qkmango.ccms.security.request.RequestURL;
import com.aliyun.dingtalkcontact_1_0.models.GetUserHeaders;
import com.aliyun.dingtalkcontact_1_0.models.GetUserResponseBody;
import com.aliyun.dingtalkoauth2_1_0.Client;
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.net.URLEncoder;

/**
 * Dingtalk 授权登陆请求
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-18 23:56
 */
public class DingtalkAuthHttpClient implements AuthHttpClient {

    //钉钉 请求配置
    private static final Config REQUEST_CONFIG = new Config().setProtocol("https").setRegionId("central");

    //钉钉配置
    private final AppConfig config;

    private final ReloadableResourceBundleMessageSource messageSource;

    public DingtalkAuthHttpClient(AppConfig config, ReloadableResourceBundleMessageSource messageSource) {
        this.config = config;
        this.messageSource = messageSource;
    }

    @Override
    public String authorize(AuthenticationAccount authAccount, String state) {

        PurposeType purpose = authAccount.getPurpose();
        RequestURL authorize = config.getAuthorize();
        String callback = config.getCallback().builder()
                // .with("purpose", purpose.name())
                .build().url();


        return authorize.builder()
                .with("response_type", "code")
                .with("prompt", "consent")
                .with("scope", "openid")
                .with("client_id", config.id)
                .with("state", state)
                .with("redirect_uri", URLEncoder.encode(callback))
                .build().url();
    }

    /**
     * 获取accessToken
     *
     * @param params 参数
     *               params[0] code
     * @return accessToken
     */
    @Override
    public String accessToken(Object... params) {
        String code = (String) params[0];
        GetUserTokenResponse response = null;
        try {
            Client client = new Client(REQUEST_CONFIG);
            GetUserTokenRequest getUserTokenRequest = new GetUserTokenRequest()
                    .setClientId(this.config.id)
                    .setClientSecret(this.config.secret)
                    .setCode(code)
                    .setGrantType("authorization_code");
            response = client.getUserToken(getUserTokenRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取用户个人token
        return response.getBody().getAccessToken();
    }

    /**
     * 获取用户信息
     *
     * @param params 参数
     *               params[0] accessToken
     * @return 用户信息
     */
    @Override
    public UserInfo userInfo(Object... params) {
        String accessToken = (String) params[0];
        //获取accessToken
        try {
            com.aliyun.dingtalkcontact_1_0.Client client = new com.aliyun.dingtalkcontact_1_0.Client(REQUEST_CONFIG);
            GetUserHeaders getUserHeaders = new GetUserHeaders();
            getUserHeaders.xAcsDingtalkAccessToken = accessToken;
            //获取用户个人信息，如需获取当前授权人的信息，unionId参数必须传me
            GetUserResponseBody userinfo = client.getUserWithOptions("me", getUserHeaders, new RuntimeOptions()).getBody();

            UserInfo info = new UserInfo();
            info.setState(true);
            info.setUid(userinfo.unionId);
            info.setName(userinfo.nick);
            info.setAvatarUrl(userinfo.avatarUrl);

            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 钉钉授权登陆
     *
     * @param state  防止CSRF攻击
     * @param code   临时授权码
     * @param params params[0] locale
     * @return 认证结果
     */
    @Override
    public AuthenticationResult authentication(String state, String code, Object... params) {
        String message = messageSource.getMessage("response.authentication.failure", null, LocaleContextHolder.getLocale());

        AuthenticationResult result = new AuthenticationResult();
        result.setSuccess(false);
        result.setMessage(message);

        //如果用户拒绝授权
        if (code == null) {
            result.setMessage(message);
            return result;
        }

        //获取 access_token
        String accessToken = this.accessToken(code);

        //获取 userInfo 信息
        UserInfo userInfo = this.userInfo(accessToken);

        //查询数据库中是否存在该用户
        //如果code无效，则Gitee返回的结果中没有 id
        if (userInfo == null) {
            result.setMessage(message);
            return result;
        }

        result.setSuccess(true);
        result.setUserInfo(userInfo);
        return result;
    }

    @Override
    public AppConfig getAppConfig() {
        return this.config;
    }
}
