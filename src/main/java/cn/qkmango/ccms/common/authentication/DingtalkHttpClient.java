package cn.qkmango.ccms.common.authentication;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse.UserInfo;
import com.taobao.api.ApiException;
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
    @Value("${ccms.authentication.dingtalk.callback}")
    private String DINGTALK_CALLBACK;
    @Value("${ccms.authentication.dingtalk.appKey}")
    private String DINGTALK_APP_KEY;
    @Value("${ccms.authentication.dingtalk.appSecret}")
    private String DINGTALK_APP_SECRET;

    @Value("${ccms.authentication.dingtalk.url.userInfo}")
    private String DINGTALK_USER_INFO_URL;

    @Value("${ccms.authentication.dingtalk.url.accessToken}")
    private String DINGTALK_ACCESS_TOKEN_URL;


    /**
     * @return 钉钉accessToken
     * @date 2022/1/26 22:00
     * @author cyb
     */
    public String getAccessToken() {
        try {
            DingTalkClient clientDingTalkClient = new DefaultDingTalkClient(DINGTALK_ACCESS_TOKEN_URL);
            OapiGettokenRequest request = new OapiGettokenRequest();
            request.setAppkey(DINGTALK_APP_KEY);
            request.setAppsecret(DINGTALK_APP_SECRET);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = clientDingTalkClient.execute(request);
            if (response.getErrcode() == 0) {
                return response.getAccessToken();
            }
            System.out.println("获取accessToken失败，错误码：" + response.getErrcode() + "，错误信息：" + response.getErrmsg());
        } catch (Exception e) {
            System.out.println("获取accessToken失败，错误信息：" + e.getMessage());
        }
        return null;
    }

    /**
     * @param code 扫码返回的code
     * @return openId
     * @date 2022/1/26 22:08
     * @author cyb
     */
    public UserInfo getUserInfo(String code) {
        DefaultDingTalkClient client = new DefaultDingTalkClient(DINGTALK_USER_INFO_URL);
        OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
        req.setTmpAuthCode(code);
        OapiSnsGetuserinfoBycodeResponse response = null;
        try {
            response = client.execute(req, DINGTALK_APP_KEY, DINGTALK_APP_SECRET);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        if (0 != response.getErrcode()) {
            return null;
        }
        return response.getUserInfo();
    }


}
