package cn.qkmango.ccms.domain.dto;

import cn.qkmango.ccms.domain.bind.trade.TradeLevel1;
import cn.qkmango.ccms.domain.bind.trade.TradeState;

/**
 * 交易DTO
 * 用于接收前端传来的交易查询条件
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-17 17:39
 */
public class TradeDto {
    private Long id;
    private Integer account;
    private Integer creator;
    private Long startCreateTime;
    private Long endCreateTime;
    private TradeState state;
    private TradeLevel1 level1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Long getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Long startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Long getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Long endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public TradeState getState() {
        return state;
    }

    public void setState(TradeState state) {
        this.state = state;
    }

    public TradeLevel1 getLevel1() {
        return level1;
    }

    public void setLevel1(TradeLevel1 level1) {
        this.level1 = level1;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "TradeDto{" +
                "id=" + id +
                ", account=" + account +
                ", creator=" + creator +
                ", startCreateTime=" + startCreateTime +
                ", endCreateTime=" + endCreateTime +
                ", state=" + state +
                ", level1=" + level1 +
                '}';
    }
}
