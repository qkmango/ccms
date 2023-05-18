package cn.qkmango.ccms.security.request;

import cn.qkmango.ccms.common.util.RedisUtil;
import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.auth.PurposeType;
import cn.qkmango.ccms.security.HttpRequestBaseURL;
import cn.qkmango.ccms.security.UserInfo;
import com.aliyun.dingtalkcontact_1_0.models.GetUserHeaders;
import com.aliyun.dingtalkcontact_1_0.models.GetUserResponseBody;
import com.aliyun.dingtalkoauth2_1_0.Client;
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import jakarta.annotation.Resource;

/**
 * Dingtalk 授权登陆请求
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-18 23:56
 */
public class DingtalkHttpRequest implements HttpRequest {

    private final Config config = new Config().setProtocol("https").setRegionId("central");

    private ClientIdAndSecret client;

    private HttpRequestBaseURL httpRequestBaseURL;

    @Resource(name = "redisUtil")
    private RedisUtil redis;

    @Override
    public String authorize(AuthenticationAccount authAccount) {
        return null;
    }

    @Override
    public String getAccessToken(String code) {
        GetUserTokenResponse response = null;
        try {
            Client client = new Client(config);
            GetUserTokenRequest getUserTokenRequest = new GetUserTokenRequest()
                    .setClientId(this.client.getId())
                    .setClientSecret(this.client.getSecret())
                    .setCode(code)
                    .setGrantType("authorization_code");
            response = client.getUserToken(getUserTokenRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取用户个人token
        return response.getBody().getAccessToken();
    }

    @Override
    public UserInfo getUserInfoByCode(String code) {
        //获取accessToken
        String accessToken = getAccessToken(code);
        try {
            com.aliyun.dingtalkcontact_1_0.Client client = new com.aliyun.dingtalkcontact_1_0.Client(config);
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

    @Override
    public void setClient(ClientIdAndSecret client) {
        this.client = client;
    }

    @Override
    public ClientIdAndSecret getClient() {
        return this.client;
    }

    @Override
    public void setHttpRequestBaseUrl(HttpRequestBaseURL url) {
        this.httpRequestBaseURL = url;
    }

    @Override
    public HttpRequestBaseURL getHttpRequestBaseUrl() {
        return this.httpRequestBaseURL;
    }


    // ===== 以下方法为 Dingtalk 未实现的方法，禁止使用 ====
    @Deprecated
    @Override
    public String getAccessToken(String code, PurposeType purpose) {
        return HttpRequest.super.getAccessToken(code, purpose);
    }

    @Deprecated
    @Override
    public UserInfo getUserInfo(String accessToken) {
        return HttpRequest.super.getUserInfo(accessToken);
    }

    @Deprecated
    @Override
    public UserInfo getUserInfoByCode(String code, PurposeType purpose) {
        return HttpRequest.super.getUserInfoByCode(code, purpose);
    }
}
