package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Update;
import cn.qkmango.ccms.domain.bind.PayState;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Date;

/**
 * 缴费记录
 * <p>住宿费等支付项目领域</p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-15 12:14
 */
public class Record implements Serializable {

    @NotEmpty(groups = {Update.class})
    private String id;

    /**
     * 缴费项目 ID
     */
    @NotEmpty(groups = {Update.class})
    private String payment;

    /**
     * 用户 ID
     */
    @NotEmpty(groups = {Update.class})
    private String user;

    /**
     * 支付状态
     */
    private PayState state;

    /**
     * 创建时间
     */
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public PayState getState() {
        return state;
    }

    public void setState(PayState state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Paid{" +
                "id='" + id + '\'' +
                ", payment='" + payment + '\'' +
                ", user='" + user + '\'' +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }
}
