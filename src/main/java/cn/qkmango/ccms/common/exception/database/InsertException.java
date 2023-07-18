package cn.qkmango.ccms.common.exception.database;

/**
 * 删除异常
 *
 * @author qkmango
 */
public class InsertException extends OperationDatabaseException {
    public InsertException() {
    }

    public InsertException(String message) {
        super(message);
    }
}
