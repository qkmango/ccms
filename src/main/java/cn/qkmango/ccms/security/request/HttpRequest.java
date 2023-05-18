package cn.qkmango.ccms.security.request;

import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.auth.PurposeType;
import cn.qkmango.ccms.security.HttpRequestBaseURL;
import cn.qkmango.ccms.security.UserInfo;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-18 21:34
 */
public interface HttpRequest {

    /**
     * 获取第三方平台认证地址
     *
     * @param authAccount 授权账户
     * @return 授权地址
     */
    String authorize(AuthenticationAccount authAccount);


    /**
     * AccessToken
     *
     * @param code 授权码
     * @return
     */
    default String getAccessToken(String code) {
        return null;
    }

    /**
     * AccessToken
     *
     * @param code    授权码
     * @param purpose 用途
     * @return
     */
    default String getAccessToken(String code, PurposeType purpose) {
        return null;
    }

    /**
     * 获取用户信息
     *
     * @param accessToken AccessToken
     * @return
     */
    default UserInfo getUserInfo(String accessToken) {
        return null;
    }

    /**
     * 获取用户信息
     *
     * @param code    授权码
     * @param purpose 用途
     * @return
     */
    default UserInfo getUserInfoByCode(String code, PurposeType purpose) {
        return null;
    }

    /**
     * 获取用户信息
     *
     * @param code 授权码
     * @return
     */
    default UserInfo getUserInfoByCode(String code) {
        return null;
    }


    void setClient(ClientIdAndSecret client);

    ClientIdAndSecret getClient();


    void setHttpRequestBaseUrl(HttpRequestBaseURL url);

    HttpRequestBaseURL getHttpRequestBaseUrl();

}
