package cn.qkmango.ccms.domain.bind;

import java.io.Serializable;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-10 17:09
 */
public enum CardState implements Serializable {

    /**
     * 正常
     */
    normal,
    /**
     * 挂失
     */
    loss,
    /**
     * 注销
     */
    canceled,

}
