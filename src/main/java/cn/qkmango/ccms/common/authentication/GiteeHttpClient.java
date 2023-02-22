package cn.qkmango.ccms.common.authentication;


import cn.qkmango.ccms.domain.bind.AuthenticationPurpose;
import com.alibaba.fastjson2.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GiteeHttpClient {

    @Value("${ccms.authentication.gitee.clientId}")
    private String GITEE_CLIENT_ID;

    @Value("${ccms.authentication.gitee.clientSecret}")
    private String GITEE_CLIENT_SECRET;

    @Value("${ccms.authentication.gitee.callback.login}")
    private String GITEE_CALLBACK_LOGIN;

    @Value("${ccms.authentication.gitee.callback.bind}")
    private String GITEE_CALLBACK_BIND;


    /**
     * 获取Access Token
     * post
     */
    public JSONObject getAccessToken(String code, AuthenticationPurpose purpose) {
        String callback = null;

        //判断授权用途，设置相应的回调地址
        switch (purpose) {
            case bind -> callback = GITEE_CALLBACK_BIND;
            default -> callback = GITEE_CALLBACK_LOGIN;
        }

        String url = "https://gitee.com/oauth/token?grant_type=authorization_code" +
                "&client_id=" + GITEE_CLIENT_ID +
                "&client_secret=" + GITEE_CLIENT_SECRET +
                "&code=" + code +
                "&redirect_uri=" + callback;

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

        try {
            HttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                String result = EntityUtils.toString(entity, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            httpPost.releaseConnection();
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * 通过临时授权码code获取用户信息
     *
     * @param code 临时授权码
     * @return
     */
    public JSONObject getUserInfoByCode(String code, AuthenticationPurpose purpose) {
        JSONObject accessToken = getAccessToken(code, purpose);
        String token = (String) accessToken.get("access_token");
        return getUserInfo(token);
    }

    /**
     * 获取用户信息
     * get
     */
    public JSONObject getUserInfo(String accessToken) {
        JSONObject jsonObject = null;
        CloseableHttpClient client = HttpClients.createDefault();

        String url = "https://gitee.com/api/v5/user?access_token=" + accessToken;

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        try {
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                jsonObject = JSONObject.parseObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return jsonObject;
    }

}
