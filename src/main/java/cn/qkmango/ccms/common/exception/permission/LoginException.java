package cn.qkmango.ccms.common.exception.permission;

/**
 * 登陆异常
 *
 * @author qkmango
 */
public class LoginException extends RuntimeException {
    public LoginException() {
        super("系统登陆异常");
    }

    public LoginException(String message) {
        super(message);
    }
}
