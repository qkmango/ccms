package cn.qkmango.ccms.common.exception;

/**
 * 更新异常
 *
 * @author qkmango
 */
public class UpdateException extends Exception {
    public UpdateException() {
        super("更新信息失败");
    }

    public UpdateException(String message) {
        super(message);
    }
}
