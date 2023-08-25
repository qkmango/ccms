package cn.qkmango.ccms.domain.bo;

/**
 * 交易支付超时
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-25 19:09
 */
public class TradePayTimeout {
    // 交易ID
    private Long trade;
    // 剩余重试次数，用于请求支付宝关单时，支付宝端系统异常
    private int retry;

    public Long getTrade() {
        return trade;
    }

    public TradePayTimeout setTrade(Long trade) {
        this.trade = trade;
        return this;
    }

    public int getRetry() {
        return retry;
    }

    public TradePayTimeout setRetry(int retry) {
        this.retry = retry;
        return this;
    }
}
