package cn.qkmango.ccms.domain.bind.trade;

/**
 * 交易状态
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-14 17:47
 */
public enum TradeState {
    /**
     * 交易成功
     */
    success,
    /**
     * 交易失败
     */
    fail,
    /**
     * 交易进行中
     */
    processing,
    /**
     * 交易已退款
     */
    refund

}
