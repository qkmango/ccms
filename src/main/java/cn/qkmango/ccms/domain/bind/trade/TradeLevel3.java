package cn.qkmango.ccms.domain.bind.trade;

/**
 * 交易三级分类
 * <p>交易类型</p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-14 17:44
 */
public enum TradeLevel3 {
    /**
     * 消费
     */
    consume,
    /**
     * 缴费
     */
    payment,
    /**
     * 退费
     */
    refund,
    /**
     * 充值
     */
    recharge,
    /**
     * 提现
     */
    withdraw,
    /**
     * 其他
     */
    other
}
