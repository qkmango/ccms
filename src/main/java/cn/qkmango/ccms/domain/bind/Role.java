package cn.qkmango.ccms.domain.bind;

import java.io.Serializable;

/**
 * 用户权限类型
 *
 * @author qkmango
 */
public enum Role implements Serializable {
    /**
     * 管理员
     */
    admin,
    /**
     * 用户
     */
    user,
    /**
     * POS刷卡机
     */
    pos

}
