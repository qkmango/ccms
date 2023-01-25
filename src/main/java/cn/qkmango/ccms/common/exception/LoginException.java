package cn.qkmango.ccms.common.exception;

/**
 * 登陆异常
 *
 * @author qkmango
 */
public class LoginException extends SystemException {
    public LoginException() {
        super("系统登陆失败！");
    }

    public LoginException(String message) {
        super(message);
    }
}
