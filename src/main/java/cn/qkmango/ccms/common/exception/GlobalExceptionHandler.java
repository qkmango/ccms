package cn.qkmango.ccms.common.exception;

import cn.qkmango.ccms.common.exception.database.OperationDatabaseException;
import cn.qkmango.ccms.common.exception.permission.PermissionException;
import cn.qkmango.ccms.common.map.R;
import jakarta.validation.ValidationException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
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
     * 常见的异常处理
     * SQL异常、权限异常、数据库操作异常、请求参数异常、缺少请求参数
     * 状态码为 409 存在冲突
     *
     * @param e 异常
     * @return 含有异常信息的
     */
    @ExceptionHandler({
            SQLException.class,
            PermissionException.class,
            OperationDatabaseException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public R<String> commonExceptionHandler(Exception e) {
        logger.warn(e.getLocalizedMessage());
        return R.fail(e.getLocalizedMessage());
    }

    /**
     * 控制器入参校验异常处理
     * 状态码 400
     *
     * @param e BindException
     * @return 含有异常信息的 R
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
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
     * 状态码 400
     *
     * @param e ValidationException
     * @return 含有异常信息的 R
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public R<String> validationExceptionHandle(ValidationException e) {
        logger.info("控制器入参校验异常处理");
        logger.warn(e.getLocalizedMessage());
        return R.fail(e.getLocalizedMessage());
    }

    /**
     * 捕获所有异常
     *
     * @param e 异常
     * @return 含有异常信息的 R
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({Throwable.class, Exception.class})
    public R<String> allThrowableHandler(Throwable e) {
        logger.warn("捕获所有异常");
        logger.warn(e.getLocalizedMessage());
        logger.warn(e.getClass().getName());
        e.printStackTrace();
        return R.fail(e.getLocalizedMessage());
    }
}
