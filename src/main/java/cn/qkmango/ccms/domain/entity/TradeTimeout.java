package cn.qkmango.ccms.domain.entity;

/**
 * 交易超时对象
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-24 20:41
 */
public class TradeTimeout {
    private Integer id;
    private Long trade;
    private Long timeout;

    public Integer getId() {
        return id;
    }

    public TradeTimeout setId(Integer id) {
        this.id = id;
        return this;
    }

    public Long getTrade() {
        return trade;
    }

    public TradeTimeout setTrade(Long trade) {
        this.trade = trade;
        return this;
    }

    public Long getTimeout() {
        return timeout;
    }

    public TradeTimeout setTimeout(Long timeout) {
        this.timeout = timeout;
        return this;
    }
}
