package cn.qkmango.ccms.common.exception.permission;

/**
 * 用户权限异常
 *
 * @author qkmango
 */
public class RoleException extends PermissionException {
    public RoleException() {
        super("用户角色异常");
    }

    public RoleException(String message) {
        super(message);
    }
}
