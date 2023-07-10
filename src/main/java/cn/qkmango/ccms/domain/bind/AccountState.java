package cn.qkmango.ccms.domain.bind;

import java.io.Serializable;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-10 15:44
 */
public enum AccountState implements Serializable {
    /**
     * 正常
     */
    normal,
    /**
     * 冻结
     */
    frozen,
    /**
     * 注销
     */
    canceled
}
