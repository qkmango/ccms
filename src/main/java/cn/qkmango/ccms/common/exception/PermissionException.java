package cn.qkmango.ccms.common.exception;

/**
 * 用户权限异常
 *
 * @author qkmango
 */
public class PermissionException extends Exception {
    public PermissionException() {
    }

    public PermissionException(String message) {
        super(message);
    }
}
