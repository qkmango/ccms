package cn.qkmango.ccms.common.exception.database;

import java.sql.SQLException;

/**
 * 操作数据库查询异常
 * <p>在对数据库的增删改时，如果影响行数与理论不符，就会抛出此异常
 * </p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-18 19:29
 */
public class OperationDatabaseException extends SQLException {
    public OperationDatabaseException() {
    }

    public OperationDatabaseException(String message) {
        super(message);
    }

    /**
     * 默认的模版
     *
     * @param theory 理论影响行数
     * @param actual 实际影响行数
     */
    public OperationDatabaseException(int theory, int actual) {
        super("删除记录异常，应影响行数:" + theory + "，实际影响行数:" + actual);
    }
}
