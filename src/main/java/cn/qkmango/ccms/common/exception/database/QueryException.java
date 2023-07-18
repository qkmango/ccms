package cn.qkmango.ccms.common.exception.database;

/**
 * @author qkmango
 */
public class QueryException extends OperationDatabaseException{
    public QueryException() {
    }

    public QueryException(String message) {
        super(message);
    }

    /**
     * 默认的模版
     * @param theory 理论影响行数
     * @param actual 实际影响行数
     */
    public QueryException(int theory, int actual) {
        super("查询记录异常，应查询行数:"+theory+"，实际查询行数:"+actual);
    }
}
