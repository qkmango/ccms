package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.common.validate.group.Query;
import cn.qkmango.ccms.common.validate.group.Update;
import cn.qkmango.ccms.domain.bind.PaymentState;
import cn.qkmango.ccms.domain.bind.PaymentType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.Date;

/**
 * 缴费项目
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-14 14:14
 */
public class Payment implements Serializable {

    @NotEmpty(groups = {Update.PaymentState.class, Update.class})
    @Pattern(regexp = "^\\d+$", groups = {Update.PaymentState.class, Update.class, Query.class})
    private String id;

    /**
     * 标题
     */
    @NotEmpty(groups = {Insert.class})
    private String title;
    /**
     * 缴费类型
     */
    @NotNull(groups = {Insert.class})
    private PaymentType type;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 发布者
     */
    private String author;
    /**
     * 缴费金额
     */
    @NotNull(groups = {Insert.class})
    @Min(value = 0, groups = {Insert.class})
    private Integer price;

    /**
     * 缴费开始时间
     */
    @NotNull(groups = {Insert.class})
    private Date startTime;

    /**
     * 缴费截止时间
     */
    @NotNull(groups = {Insert.class})
    private Date endTime;

    /**
     * 项目状态
     */
    @NotNull(groups = {Insert.class,Update.PaymentState.class})
    private PaymentState state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public PaymentState getState() {
        return state;
    }

    public void setState(PaymentState state) {
        this.state = state;
    }


    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", state=" + state +
                '}';
    }
}
