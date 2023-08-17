package cn.qkmango.ccms.domain.dto;

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
    private Long startCreateTime;
    private Long endCreateTime;

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

    @Override
    public String toString() {
        return "TradeDto{" +
                "id=" + id +
                ", account=" + account +
                ", startCreateTime=" + startCreateTime +
                ", endCreateTime=" + endCreateTime +
                '}';
    }
}
