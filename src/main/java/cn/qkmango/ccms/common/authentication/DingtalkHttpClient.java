package cn.qkmango.ccms.common.authentication;

import com.aliyun.dingtalkcontact_1_0.models.GetUserHeaders;
import com.aliyun.dingtalkcontact_1_0.models.GetUserResponseBody;
import com.aliyun.dingtalkoauth2_1_0.Client;
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetUserTokenResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 钉钉授权登陆请求
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-20 11:49
 */
@Component
public class DingtalkHttpClient {
    @Value("${ccms.authentication.dingtalk.appKey}")
    private String DINGTALK_APP_KEY;
    @Value("${ccms.authentication.dingtalk.appSecret}")
    private String DINGTALK_APP_SECRET;

    private final String DINGTALK_USER_INFO_URL = "https://oapi.dingtalk.com/sns/getuserinfo_bycode";
    private final String DINGTALK_ACCESS_TOKEN_URL = "https://oapi.dingtalk.com/gettoken";
    private final Config config = new Config().setProtocol("https").setRegionId("central");


    /**
     * 钉钉accessToken
     *
     * @param authCode
     * @return 钉钉accessToken
     */
    public String getAccessToken(String authCode) {
        GetUserTokenResponse response = null;
        try {
            Client client = new Client(config);
            GetUserTokenRequest getUserTokenRequest = new GetUserTokenRequest()
                    .setClientId(DINGTALK_APP_KEY)
                    .setClientSecret(DINGTALK_APP_SECRET)
                    .setCode(authCode)
                    .setGrantType("authorization_code");
            response = client.getUserToken(getUserTokenRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取用户个人token
        return response.getBody().getAccessToken();
    }

    /**
     * @param code 钉钉授权码
     * @return openId
     * @date 2022/1/26 22:08
     * @author cyb
     */
    public GetUserResponseBody getUserInfo(String code) {
        //获取accessToken
        String accessToken = getAccessToken(code);
        GetUserResponseBody userinfo = null;
        try {
            com.aliyun.dingtalkcontact_1_0.Client client = new com.aliyun.dingtalkcontact_1_0.Client(config);
            GetUserHeaders getUserHeaders = new GetUserHeaders();
            getUserHeaders.xAcsDingtalkAccessToken = accessToken;
            //获取用户个人信息，如需获取当前授权人的信息，unionId参数必须传me
            userinfo = client.getUserWithOptions("me", getUserHeaders, new RuntimeOptions()).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userinfo;
    }


}
