package cn.qkmango.ccms.web.interceptor;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.util.ResponseUtil;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.security.holder.AccountHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;


/**
 * 用户权限验证
 * <p>
 * 1. 类和方法上都没有注解，则说明没有设置接口权限，则无需拦截，返回true放行<br>
 * 2. 首先判断方法上是否有注解，如果有，则使用方法上的注解<br>
 * 3. 如果方法上没有注解，则使用类上的注解<br>
 * 4. 方法的注解优先于类的注解
 *
 * @author qkmango
 */
public class PermissionsInterceptor implements HandlerInterceptor {

    private final String OPERATION_WITHOUT_ROLE_JSON = "{\"success\":false,\"code\":400,\"message\":\"无权操作\"}";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //获取类
        Class<?> clazz = method.getDeclaringClass();

        //获取用户的权限类型
        Role role = AccountHolder.getRole();

        //判断方法是否有注解
        if (method.isAnnotationPresent(Permission.class)) {
            boolean pass = methodHasPermissionAnnotation(method, role);
            if (pass) {
                return true;
            }
            response.setStatus(401);
            ResponseUtil.responseJson(response, OPERATION_WITHOUT_ROLE_JSON);
            return false;
        }

        //判断类是否有注解
        else if (clazz.isAnnotationPresent(Permission.class)) {
            boolean pass = classHasPermissionAnnotation(clazz, role);
            if (pass) {
                return true;
            }
            response.setStatus(401);
            ResponseUtil.responseJson(response, OPERATION_WITHOUT_ROLE_JSON);
            return false;
        }

        //如果类和方法都没有注解，则说明没有设置接口权限，则无需拦截,返回true放行
        else {
            return true;
        }
    }


    /**
     * 判断类是否由指定用户权限的注解
     *
     * @param clazz                  类
     * @param thisUserRole 用户权限
     * @return true 有权限，false 无权限
     */
    private boolean classHasPermissionAnnotation(Class<?> clazz, Role thisUserRole) {
        if (clazz.isAnnotationPresent(Permission.class)) {
            Permission annotation = clazz.getAnnotation(Permission.class);
            Role[] value = annotation.value();
            for (Role role : value) {
                if (role == thisUserRole) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断方法是否由指定用户权限的注解
     *
     * @param method                 方法
     * @param thisUserRole 用户权限
     * @return true 有权限，false 无权限
     */
    private boolean methodHasPermissionAnnotation(Method method, Role thisUserRole) {
        if (method.isAnnotationPresent(Permission.class)) {
            Permission annotation = method.getAnnotation(Permission.class);
            Role[] value = annotation.value();
            for (Role role : value) {
                if (role == thisUserRole) {
                    return true;
                }
            }
        }
        return false;
    }

}
