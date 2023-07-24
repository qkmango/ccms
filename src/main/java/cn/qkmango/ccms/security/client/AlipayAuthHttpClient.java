package cn.qkmango.ccms.security.client;

import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.security.AuthenticationResult;
import cn.qkmango.ccms.security.UserInfo;
import cn.qkmango.ccms.security.config.AlipayAppConfig;
import cn.qkmango.ccms.security.config.AppConfig;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.net.URLEncoder;

/**
 * 支付宝认证客户端
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-20 11:49
 */
public class AlipayAuthHttpClient implements AuthHttpClient {

    private final AlipayAppConfig config;

    private final ReloadableResourceBundleMessageSource messageSource;


    public AlipayAuthHttpClient(AlipayAppConfig config, ReloadableResourceBundleMessageSource messageSource) {
        this.config = config;
        this.messageSource = messageSource;
    }

    @Override
    public String authorize(AuthenticationAccount authAccount, String state) {
        String callback = config.getCallback().builder()
                // .with("purpose", authAccount.getPurpose().name())
                .build().url();

        return config.getAuthorize().builder()
                .with("app_id", config.getId())
                .with("scope", "auth_user")
                .with("redirect_uri", URLEncoder.encode(callback))
                .with("state", state)
                .build().url();
    }

    /**
     * @param params 参数
     *               params[0] = code
     *               params[1] = purpose
     * @return accessToken
     */
    @Override
    public String accessToken(Object... params) {
        AlipayClient alipayClient = new DefaultAlipayClient(
                config.gateway,
                config.id,
                config.appPrivateKey,
                "json",
                "utf-8",
                config.alipayPublicKey,
                "RSA2");

        String code = (String) params[0];

        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(code);
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            return oauthTokenResponse.getAccessToken();
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取用户信息
     *
     * @param params 参数
     *               params[0] = accessToken
     * @return 用户信息
     */
    @Override
    public UserInfo userInfo(Object... params) {

        String accessToken = (String) params[0];

        AlipayClient alipayClient = new DefaultAlipayClient(
                config.gateway,
                config.id,
                config.appPrivateKey,
                "json",
                "utf-8",
                config.alipayPublicKey,
                "RSA2");

        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse response = null;
        try {
            response = alipayClient.execute(request, accessToken);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }

        if (null != response && response.isSuccess()) {
            UserInfo info = new UserInfo();

            info.setState(true);
            info.setUid(response.getUserId());
            info.setName(response.getNickName());
            info.setAvatarUrl(response.getAvatar());
            return info;
        }
        return null;
    }

    /**
     * 认证
     *
     * @param state  防止CSRF攻击
     * @param code   临时授权码
     * @param params params[0] = AuthenticationAccount
     *               params[1] = locale
     * @return 认证结果
     */
    @Override
    public AuthenticationResult authentication(String state, String code, Object... params) {
        AuthenticationAccount account = (AuthenticationAccount) params[0];

        //获取授权用途
        // PurposeType purpose = account.getPurpose();

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
        return config;
    }
}
