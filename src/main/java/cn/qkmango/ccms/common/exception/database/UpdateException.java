package cn.qkmango.ccms.common.exception.database;

/**
 * 更新异常
 *
 * @author qkmango
 */
public class UpdateException extends OperationDatabaseException {
    public UpdateException() {
        super("更新信息失败");
    }

    public UpdateException(String message) {
        super(message);
    }
}
