package cn.qkmango.ccms.domain;

import io.micrometer.common.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Domain 基类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-04-26 22:24
 */
public interface BaseDomain extends Serializable {
    /**
     * 判断属性是否全为空
     */
    default boolean isAllNull() {
        try {
            for (Field f : this.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                Object o = f.get(this);
                if (o != null && StringUtils.isNotBlank(o.toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
