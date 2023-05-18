package cn.qkmango.ccms.security.request;

import cn.qkmango.ccms.common.util.RedisUtil;
import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.auth.PurposeType;
import cn.qkmango.ccms.security.HttpRequestBaseURL;
import cn.qkmango.ccms.security.UserInfo;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Gitee 授权登陆请求
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-18 21:40
 */
public class GiteeHttpRequest implements HttpRequest {


    private ClientIdAndSecret client;

    private HttpRequestBaseURL httpRequestBaseURL;

    @Resource(name = "redisUtil")
    private RedisUtil redis;

    @Override
    public String authorize(AuthenticationAccount authAccount) {
        // 用于第三方应用防止CSRF攻击
        String state = "auth:" + UUID.randomUUID();
        String value = JSONObject.toJSONString(authAccount);

        //将 uuid 存入redis，用于第三方应用防止CSRF攻击，有效期为5分钟
        redis.set(state, value, 60 * 5);

        //获取 第三方平台 和 授权用途
        // PlatformType platform = authAccount.getPlatform();
        PurposeType purpose = authAccount.getPurpose();

        //重定向地址
        String redirect = httpRequestBaseURL.getCallbackURL(purpose).url();

        //授权地址
        return httpRequestBaseURL.getAuthorizeURL().builder()
                .with("response_type", "code")
                .with("scope", "user_info")
                .with("client_id", getClient().getId())
                .with("state", state)
                .with("redirect_uri", URLEncoder.encode(redirect))
                .build();
    }


    @Override
    public String getAccessToken(String code, PurposeType purpose) {

        //判断授权用途，设置相应的回调地址
        String redirect = this.httpRequestBaseURL.getCallbackURL(purpose).url();

        // String url = "https://gitee.com/oauth/token?grant_type=authorization_code" +
        //         "&client_id=" +  +
        //         "&client_secret=" + getClient().getSecret() +
        //         "&code=" + code +
        //         "&redirect_uri=" + callback;

        // String url = accessToken

        String url = httpRequestBaseURL.getAccessTokenURL().builder()
                .with("client_id", getClient().getId())
                .with("client_secret", getClient().getSecret())
                .with("code", code)
                .with("redirect_uri", redirect)
                .build();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

        try {
            HttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                String result = EntityUtils.toString(entity, "UTF-8");
                JSONObject parsed = JSONObject.parseObject(result);
                return (String) parsed.get("access_token");
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

    @Override
    public UserInfo getUserInfo(String accessToken) {

        CloseableHttpClient client = HttpClients.createDefault();

        // String url = "https://gitee.com/api/v5/user?access_token=" + accessToken;

        String url = httpRequestBaseURL.getUserInfoURL()
                .builder()
                .with("access_token", accessToken)
                .build();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        try {
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                JSONObject jsonObject = JSONObject.parseObject(result);

                UserInfo user = new UserInfo();
                user.setState(true);
                user.setUid((String) jsonObject.get("id"));
                user.setName((String) jsonObject.get("name"));
                user.setAvatarUrl((String) jsonObject.get("avatar_url"));
                return user;
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

        return null;
    }

    @Override
    public UserInfo getUserInfoByCode(String code, PurposeType purpose) {
        String token = getAccessToken(code, purpose);
        return getUserInfo(token);
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

}
