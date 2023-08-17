package cn.qkmango.ccms.domain.vo;

import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.Department;
import cn.qkmango.ccms.domain.entity.Trade;

import java.util.LinkedList;

/**
 * 交易详情
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-17 21:38
 */
public class TradeVO {
    // 交易信息
    private Trade trade;
    // 交易账户信息
    private Account payer;
    // 收款方信息
    private Account creator;
    // 交易账户所属部门链
    private LinkedList<Department> payerDeptChain;
    // 收款方所属部门链
    private LinkedList<Department> creatorDeptChain;

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public Account getPayer() {
        return payer;
    }

    public void setPayer(Account payer) {
        this.payer = payer;
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    public LinkedList<Department> getPayerDeptChain() {
        return payerDeptChain;
    }

    public void setPayerDeptChain(LinkedList<Department> payerDeptChain) {
        this.payerDeptChain = payerDeptChain;
    }

    public LinkedList<Department> getCreatorDeptChain() {
        return creatorDeptChain;
    }

    public void setCreatorDeptChain(LinkedList<Department> creatorDeptChain) {
        this.creatorDeptChain = creatorDeptChain;
    }

    @Override
    public String toString() {
        return "TradeVO{" +
                "trade=" + trade +
                ", payerAccount=" + payer +
                ", creatorAccount=" + creator +
                ", payerDeptChain=" + payerDeptChain +
                ", creatorDeptChain=" + creatorDeptChain +
                '}';
    }
}
