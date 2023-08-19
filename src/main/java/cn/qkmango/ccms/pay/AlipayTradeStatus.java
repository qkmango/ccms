package cn.qkmango.ccms.pay;

/**
 * 支付宝交易状态说明
 * <p>
 *     TRADE_SUCCESS 或 TRADE_FINISHED 时，支付宝才会认定为买家付款成功。
 *     另外如果签约的产品支持退款，并且对应的产品默认支持能收到 TRADE_SUCCESS 或 TRADE_FINISHED 状态，
 *     该笔会先收到 TRADE_SUCCESS 交易状态，
 *     然后超过 交易有效退款时间 该笔交易会再次收到 TRADE_FINISHED 状态，
 *     实际该笔交易只支付了一次，切勿认为该笔交易支付两次
 * </p>
 * @author qkmango
 * @version 1.0
 * @date 2023-08-19 20:22
 */
public enum AlipayTradeStatus {
    // 交易创建，等待买家付款
    WAIT_BUYER_PAY,
    // 未付款交易超时关闭，或支付完成后全额退款
    TRADE_CLOSED,
    // 交易支付成功
    TRADE_SUCCESS,
    // 交易结束，不可退款
    TRADE_FINISHED
}
