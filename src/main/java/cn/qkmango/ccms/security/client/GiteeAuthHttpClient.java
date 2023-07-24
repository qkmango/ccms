package cn.qkmango.ccms.security.client;

import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.auth.PurposeType;
import cn.qkmango.ccms.security.AuthenticationResult;
import cn.qkmango.ccms.security.UserInfo;
import cn.qkmango.ccms.security.config.AppConfig;
import cn.qkmango.ccms.security.request.RequestURL;
import com.alibaba.fastjson2.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Gitee 授权登陆请求
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-18 21:40
 */
public class GiteeAuthHttpClient implements AuthHttpClient {

    private final AppConfig config;

    private final ReloadableResourceBundleMessageSource messageSource;

    public GiteeAuthHttpClient(AppConfig config, ReloadableResourceBundleMessageSource messageSource) {
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
                .with("scope", "user_info")
                .with("client_id", config.id)
                .with("state", state)
                .with("redirect_uri", URLEncoder.encode(callback))
                .build().url();
    }

    /**
     * 获取用户信息
     *
     * @param params 参数
     *               第一个参数为 String code
     *               第二个参数为 PurposeType purpose
     * @return 用户信息
     */
    @Override
    public String accessToken(Object... params) {
        String code = (String) params[0];

        String callback = config.getCallback().builder()
                .build().url();

        RequestURL accessToken = config.getAccessToken();
        String url = accessToken.builder()
                .with("client_id", config.id)
                .with("client_secret", config.secret)
                .with("grant_type", "authorization_code")
                .with("code", code)
                .with("redirect_uri", URLEncoder.encode(callback))
                .build().url();

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

    /**
     * 获取用户信息
     *
     * @param params 参数
     *               params[0] 为 String accessToken
     * @return 用户信息
     */
    @Override
    public UserInfo userInfo(Object... params) {

        String accessToken = (String) params[0];
        CloseableHttpClient client = HttpClients.createDefault();

        // String url = "https://gitee.com/api/v5/user?access_token=" + accessToken;

        RequestURL userInfo = config.getUserInfo();

        String url = userInfo.builder()
                .with("access_token", accessToken)
                .build().url();

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
                user.setUid(jsonObject.get("id").toString());
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


    /**
     * @param state  state
     * @param code   code
     * @param params params[0] error
     * @return
     */
    @Override
    public AuthenticationResult authentication(String state, String code, Object... params) {

        String error = (String) params[0];

        String message = messageSource.getMessage("response.authentication.failure", null, LocaleContextHolder.getLocale());

        AuthenticationResult result = new AuthenticationResult();
        result.setSuccess(false);
        result.setMessage(message);

        //如果用户拒绝授权
        if (error != null || code == null) {
            return result;
        }

        //获取 access_token
        String accessToken = this.accessToken(code);

        //获取 userInfo 信息
        UserInfo userInfo = this.userInfo(accessToken);

        //查询数据库中是否存在该用户
        //如果code无效，则Gitee返回的结果中没有 id
        if (userInfo == null) {
            return result;
        }

        result.setSuccess(true);
        result.setUserInfo(userInfo);
        result.setMessage(messageSource.getMessage("response.authentication.success", null, LocaleContextHolder.getLocale()));
        return result;
    }

    @Override
    public AppConfig getAppConfig() {
        return this.config;
    }
}
