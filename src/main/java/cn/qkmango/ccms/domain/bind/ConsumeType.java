package cn.qkmango.ccms.domain.bind;

import java.io.Serializable;

/**
 * 消费类型
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-31 22:40
 */
public enum ConsumeType implements Serializable {

    /**
     * 水费
     */
    WATER,
    /**
     * 电费
     */
    ELECTRIC,
    /**
     * 餐费
     */
    MEAL_EXPENSE,
    /**
     * 缴费
     */
    PAYMENT,
    /**
     * 充值
     */
    RECHARGE,
    /**
     * 退款
     */
    REFUND,
    /**
     * 其他
     */
    OTHER
}
