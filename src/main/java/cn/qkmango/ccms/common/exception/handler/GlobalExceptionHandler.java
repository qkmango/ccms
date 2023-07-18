package cn.qkmango.ccms.common.exception.handler;

import cn.qkmango.ccms.common.exception.*;
import cn.qkmango.ccms.common.map.R;

import jakarta.validation.ValidationException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;


/**
 * 全局异常处理类
 *
 * @author qkmango
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    /**
     * 异常信息集合
     */
    public static final HashMap<String, String> EXCEPTION_MESSAGE = new HashMap<>();

    //下面可以使用 i18n 国际化文件来实现
    static {
        EXCEPTION_MESSAGE.put("org.springframework.dao.DataIntegrityViolationException", "数据库操作异常: 违反完整性约束, 可能是外键约束, 请检查该是否有其他数据引用此条数据");
        EXCEPTION_MESSAGE.put("org.springframework.jdbc.BadSqlGrammarException", "SQL语法错误");
        EXCEPTION_MESSAGE.put("org.springframework.web.bind.MissingServletRequestParameterException", "缺少请求参数");
    }

    /**
     * 常见的异常处理
     *
     * @param e 异常
     * @return 含有异常信息的
     */
    @ExceptionHandler({
            SQLException.class,
            LoginException.class,
            SystemException.class,
            RoleException.class,
            UpdateException.class,
            InsertException.class,
            DeleteException.class,
            QueryException.class,
            HttpMessageNotReadableException.class
    })
    //设置响应状态码为 500
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<String> commonExceptionHandler(Exception e) {
        logger.warn(e.getMessage());
        return R.fail(e.getMessage());
    }

    /**
     * 控制器入参校验异常处理
     *
     * @param e BindException
     * @return 含有异常信息的 R
     */
    @ExceptionHandler(BindException.class)
    public R<String> bindExceptionHandle(BindException e) {
        List<ObjectError> errors = e.getAllErrors();
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        errors.forEach(er -> joiner.add(er.getDefaultMessage()));
        String message = joiner.toString();
        logger.warn(message);

        return R.fail(message);
    }

    /**
     * 控制器入参校验异常处理
     * 直接在控制器入参上使用相关的约束注解，抛出的异常
     *
     * @param e ValidationException
     * @return 含有异常信息的 R
     */
    @ExceptionHandler(ValidationException.class)
    public R<String> validationExceptionHandle(ValidationException e) {
        logger.warn(e.getMessage());
        return R.fail(e.getMessage());
    }

    /**
     * 捕获所有异常
     *
     * @param e 异常
     * @return 含有异常信息的 R
     */
    @ExceptionHandler({Throwable.class, Exception.class})
    public R<String> allThrowableHandler(Throwable e) {
        logger.warn(e.getMessage());
        System.out.println("=====> " + e.getClass().getName());

        //获取异常的类型名称，然后根据类型名称从异常信息集合中获取对应的异常信息
        String message = EXCEPTION_MESSAGE.get(e.getClass().getName());
        //如果异常信息集合中没有对应的异常信息，则使用默认的异常信息
        if (message == null) {
            message = e.getMessage();
        } else {
            //如果异常信息集合中有对应的异常信息，则将其也打印出来
            logger.warn(message);
        }

        e.printStackTrace();
        return R.fail(message);
    }
}
