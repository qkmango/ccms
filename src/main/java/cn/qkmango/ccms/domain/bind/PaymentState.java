package cn.qkmango.ccms.domain.bind;

import java.io.Serializable;

/**
 * 缴费状态
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-14 14:31
 */
public enum PaymentState implements Serializable {

    /**
     * 未开始
     */
    NOT_START,
    /**
     * 进行中
     */
    IN_PROGRESS,
    /**
     * 已结束
     */
    END,
    /**
     * 已取消
     */
    CANCEL,
    /**
     * 已暂停
     */
    PAUSE,

}
