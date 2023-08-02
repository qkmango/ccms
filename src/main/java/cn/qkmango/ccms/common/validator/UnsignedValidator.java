package cn.qkmango.ccms.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 无符号整数校验器
 * <p>用于校验数字是否为正整数/无符号整数</p>
 * <p>支持的类型：Byte、Short、Integer、Long</p>
 * <p>如果是null，返回true</p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-02 22:00
 */
public class UnsignedValidator implements ConstraintValidator<Unsigned, Number> {
    @Override
    public void initialize(Unsigned constraintAnnotation) {
        // 初始化操作，如果有需要可以在这里进行一些初始化
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        // 校验逻辑：判断数值是否大于等于0
        if (value instanceof Byte) {
            return (Byte) value >= 0;
        }

        if (value instanceof Short) {
            return (Short) value >= 0;
        }

        if (value instanceof Integer) {
            return (Integer) value >= 0;
        }

        if (value instanceof Long) {
            return (Long) value >= 0;
        }

        return false;
    }
}