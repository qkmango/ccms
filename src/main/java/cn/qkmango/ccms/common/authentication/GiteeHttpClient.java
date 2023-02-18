package cn.qkmango.ccms.common.authentication;


import com.alibaba.fastjson2.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
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

    @Value("${ccms.authentication.gitee.callback}")
    private String GITEE_CALLBACK;


    /**
     * 获取Access Token
     * post
     */
    public JSONObject getAccessToken(String code) {

        String url = "https://gitee.com/oauth/token?grant_type=authorization_code" +
                "&client_id=" + GITEE_CLIENT_ID +
                "&client_secret=" + GITEE_CLIENT_SECRET +
                "&code=" + code +
                "&redirect_uri=" + GITEE_CALLBACK;

        HttpClient client = HttpClients.createDefault();
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
        }

        httpPost.releaseConnection();
        return null;
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
            throw new RuntimeException(e);
        }

        httpGet.releaseConnection();

        return jsonObject;
    }

}
