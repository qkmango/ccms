package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.domain.BaseDomain;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel1;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel2;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel3;
import cn.qkmango.ccms.domain.bind.trade.TradeState;

/**
 * 交易
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-14 16:53
 */
public class Trade extends BaseDomain<Trade> {

    private Long id;
    private Integer account;
    private TradeLevel1 level1;
    private TradeLevel2 level2;
    private TradeLevel3 level3;
    private TradeState state;


    /**
     * 外部系统的ID
     * 如微信订单号，支付宝订单号
     */
    private String outId;

    /**
     * 内部系统的ID
     * 如 缴费项目ID
     */
    private Long inId;

    /**
     * 创建者
     */
    private Integer creator;

    private Integer amount;
    private Long createTime;
    private String description;

    public Long getId() {
        return id;
    }

    public Trade setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getAccount() {
        return account;
    }

    public Trade setAccount(Integer account) {
        this.account = account;
        return this;
    }

    public TradeLevel1 getLevel1() {
        return level1;
    }

    public Trade setLevel1(TradeLevel1 level1) {
        this.level1 = level1;
        return this;
    }

    public TradeLevel2 getLevel2() {
        return level2;
    }

    public Trade setLevel2(TradeLevel2 level2) {
        this.level2 = level2;
        return this;
    }

    public TradeLevel3 getLevel3() {
        return level3;
    }

    public Trade setLevel3(TradeLevel3 level3) {
        this.level3 = level3;
        return this;
    }

    public TradeState getState() {
        return state;
    }

    public Trade setState(TradeState state) {
        this.state = state;
        return this;
    }

    public String getOutId() {
        return outId;
    }

    public Trade setOutId(String outId) {
        this.outId = outId;
        return this;
    }

    public Long getInId() {
        return inId;
    }

    public Trade setInId(Long inId) {
        this.inId = inId;
        return this;
    }

    public Integer getAmount() {
        return amount;
    }

    public Trade setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public Trade setCreateTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Trade setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getCreator() {
        return creator;
    }

    public Trade setCreator(Integer creator) {
        this.creator = creator;
        return this;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "id=" + id +
                ", account=" + account +
                ", level1=" + level1 +
                ", level2=" + level2 +
                ", level3=" + level3 +
                ", state=" + state +
                ", outId='" + outId + '\'' +
                ", inId=" + inId +
                ", creator=" + creator +
                ", amount=" + amount +
                ", createTime=" + createTime +
                ", description='" + description + '\'' +
                ", version=" + getVersion() +
                '}';
    }
}