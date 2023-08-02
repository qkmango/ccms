package cn.qkmango.ccms.common.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 自定义注解，用于校验数字是否为正整数/无符号整数
 * @author qkmango
 */
@Documented
@Constraint(validatedBy = UnsignedValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unsigned {
    String message() default "Invalid unsigned value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

