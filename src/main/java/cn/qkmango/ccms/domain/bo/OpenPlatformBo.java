package cn.qkmango.ccms.domain.bo;

import cn.qkmango.ccms.domain.entity.OpenPlatform;

/**
 * 开放平台业务对象
 * 多了一个 bind 属性，用于表示该平台是否绑定了账号
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-26 13:01
 */
public class OpenPlatformBo extends OpenPlatform {
    Boolean bind;

    public Boolean getBind() {
        return bind;
    }

    public OpenPlatformBo setBind(Boolean bind) {
        this.bind = bind;
        return this;
    }

    @Override
    public String toString() {
        return "OpenPlatformBo{" +
                "bind=" + bind +
                ", id=" + getId() +
                ", account=" + getAccount() +
                ", type=" + getType() +
                ", uid='" + getUid() + '\'' +
                '}';
    }
}
