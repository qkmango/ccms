package cn.qkmango.ccms.domain;

import java.io.Serializable;

/**
 * domain基类
 * <p>
 * 所有domain类都继承自该类
 *
 * </p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-18 11:20
 */
public abstract class BaseDomain<T> implements Serializable {

    private Integer version;
    private Integer newVersion;

    public Integer getVersion() {
        return version;
    }

    public T setVersion(Integer version) {
        this.version = version;
        return (T) this;
    }

    public Integer getNewVersion() {
        return newVersion;
    }

    public T setNewVersion(Integer newVersion) {
        this.newVersion = newVersion;
        return (T)this;
    }
}
