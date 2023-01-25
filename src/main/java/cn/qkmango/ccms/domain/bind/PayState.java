package cn.qkmango.ccms.domain.bind;

import java.io.Serializable;

/**
 * 支付状态
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-15 12:16
 */
public enum PayState implements Serializable {
    /**
     * 已支付
     */
    PAID,
    /**
     * 未支付
     */
    UNPAID,
    /**
     * 已退款
     */
    REFUNDED
}
