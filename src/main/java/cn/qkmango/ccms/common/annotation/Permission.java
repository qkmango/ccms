package cn.qkmango.ccms.common.annotation;


import cn.qkmango.ccms.domain.bind.PermissionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 用户权限控制注解
 * <p>在控制器方法上使用，指定控制器方法允许的用户类型
 *
 * @author qkmango
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

    PermissionType[] value() default {
            PermissionType.user,
            PermissionType.admin,
            PermissionType.pos
    };
}
