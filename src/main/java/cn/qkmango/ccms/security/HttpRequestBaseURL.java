package cn.qkmango.ccms.security;

import cn.qkmango.ccms.domain.auth.PurposeType;
import cn.qkmango.ccms.security.request.RequestURL;

/**
 * 认证请求的基础常用 URL
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-18 23:16
 */
public class HttpRequestBaseURL {
    private RequestURL authorizeURL;
    private RequestURL accessTokenURL;
    // private RequestURL callbackLoginURL;
    // private RequestURL callbackBindURL;
    private RequestURL callbackLoginURL;
    private RequestURL callbackBindURL;
    private RequestURL userInfoURL;

    public RequestURL getAuthorizeURL() {
        return authorizeURL;
    }

    public void setAuthorizeURL(RequestURL authorizeURL) {
        this.authorizeURL = authorizeURL;
    }

    public RequestURL getAccessTokenURL() {
        return accessTokenURL;
    }

    public void setAccessTokenURL(RequestURL accessTokenURL) {
        this.accessTokenURL = accessTokenURL;
    }

    public RequestURL getCallbackLoginURL() {
        return callbackLoginURL;
    }

    public void setCallbackLoginURL(RequestURL callbackLoginURL) {
        this.callbackLoginURL = callbackLoginURL;
    }

    public RequestURL getCallbackBindURL() {
        return callbackBindURL;
    }

    public void setCallbackBindURL(RequestURL callbackBindURL) {
        this.callbackBindURL = callbackBindURL;
    }

    public RequestURL getUserInfoURL() {
        return userInfoURL;
    }

    public void setUserInfoURL(RequestURL userInfoURL) {
        this.userInfoURL = userInfoURL;
    }

    public RequestURL getCallbackURL(PurposeType purpose) {
        switch (purpose) {
            case login -> {
                return callbackLoginURL;
            }
            case bind -> {
                return callbackBindURL;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "HttpRequestBaseURL{" +
                "authorizeURL=" + authorizeURL +
                ", accessTokenURL=" + accessTokenURL +
                ", redirectLoginURL=" + callbackLoginURL +
                ", redirectBindURL=" + callbackBindURL +
                ", userInfoURL=" + userInfoURL +
                '}';
    }
}
