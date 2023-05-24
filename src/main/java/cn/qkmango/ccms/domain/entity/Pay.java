package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.domain.bind.PayType;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付充值
 *
 * @author qkmango-
 * @version 1.0
 * @date 2023-05-24 18:18
 */
public class Pay implements Serializable {

    private String id;

    //用户ID
    private String user;

    //金额
    private Integer amount;

    //支付类型
    private PayType type;

    //创建时间
    private Date createTime;

    //是否已经完成
    private Boolean done;

    //支付宝交易号
    private String tradeNo;

    //备注
    private String info;

    //支付详情，可以将支付宝返回的信息JSON化存储在这里
    private String detail;

    public String getDetail() {
        return detail;
    }

    public Pay setDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public String getId() {
        return id;
    }

    public Pay setId(String id) {
        this.id = id;
        return this;
    }

    public String getUser() {
        return user;
    }

    public Pay setUser(String user) {
        this.user = user;
        return this;
    }

    public Integer getAmount() {
        return amount;
    }

    public Pay setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public PayType getType() {
        return type;
    }

    public Pay setType(PayType type) {
        this.type = type;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Pay setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public Pay setInfo(String info) {
        this.info = info;
        return this;
    }

    public Boolean getDone() {
        return done;
    }

    public Pay setDone(Boolean done) {
        this.done = done;
        return this;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public Pay setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
        return this;
    }


    @Override
    public String toString() {
        return "Pay{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                ", createTime=" + createTime +
                ", done=" + done +
                ", tradeNo='" + tradeNo + '\'' +
                ", info='" + info + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
