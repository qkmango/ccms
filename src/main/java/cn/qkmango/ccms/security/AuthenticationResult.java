package cn.qkmango.ccms.security;

/**
 * 认证结果
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-19 13:25
 */
public class AuthenticationResult {
    public boolean success;
    public String message;
    public UserInfo userInfo;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "AuthenticationResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }
}
