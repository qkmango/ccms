package cn.qkmango.ccms.common.exception.permission;

/**
 * 权限异常
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-18 19:35
 */
public class PermissionException extends Exception {
    public PermissionException() {
        super("权限异常");
    }

    public PermissionException(String message) {
        super(message);
    }
}
