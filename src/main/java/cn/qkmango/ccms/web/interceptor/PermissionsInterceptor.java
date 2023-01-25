package cn.qkmango.ccms.web.interceptor;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.util.ResponseUtil;
import cn.qkmango.ccms.domain.bind.PermissionType;
import cn.qkmango.ccms.domain.constant.Constant;
import cn.qkmango.ccms.domain.entity.Account;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //如果如果是登陆接口则放行
        String path = request.getServletPath();
        if (Constant.LOGIN_URL.equals(path)) {
            return true;
        }

        //获取方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //获取类
        Class<?> clazz = method.getDeclaringClass();

        //获取用户的权限类型
        Account account = (Account) request.getSession().getAttribute("account");
        PermissionType permissionType = account.getPermissionType();

        //判断方法是否有注解
        if (method.isAnnotationPresent(Permission.class)) {
            boolean pass = methodHasPermissionAnnotation(method, permissionType);
            if (pass) {
                return true;
            }
            response.setStatus(401);
            ResponseUtil.responseJson(response, Constant.OPERATION_WITHOUT_PERMISSION_JSON);
            return false;
        }

        //判断类是否有注解
        else if (clazz.isAnnotationPresent(Permission.class)) {
            boolean pass = classHasPermissionAnnotation(clazz, permissionType);
            if (pass) {
                return true;
            }
            response.setStatus(401);
            ResponseUtil.responseJson(response, Constant.OPERATION_WITHOUT_PERMISSION_JSON);
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
     * @param thisUserPermissionType 用户权限
     * @return true 有权限，false 无权限
     */
    private boolean classHasPermissionAnnotation(Class<?> clazz, PermissionType thisUserPermissionType) {
        if (clazz.isAnnotationPresent(Permission.class)) {
            Permission annotation = clazz.getAnnotation(Permission.class);
            PermissionType[] value = annotation.value();
            for (PermissionType permissionType : value) {
                if (permissionType == thisUserPermissionType) {
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
     * @param thisUserPermissionType 用户权限
     * @return true 有权限，false 无权限
     */
    private boolean methodHasPermissionAnnotation(Method method, PermissionType thisUserPermissionType) {
        if (method.isAnnotationPresent(Permission.class)) {
            Permission annotation = method.getAnnotation(Permission.class);
            PermissionType[] value = annotation.value();
            for (PermissionType permissionType : value) {
                if (permissionType == thisUserPermissionType) {
                    return true;
                }
            }
        }
        return false;
    }

}
