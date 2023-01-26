package cn.qkmango.ccms.domain.param;

import cn.qkmango.ccms.domain.entity.Payment;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 缴费项目查询参数
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-17 18:58
 */
public class PaymentParam extends Payment {
    private Integer minPrice;
    private Integer maxPrice;
    //用户ID
    private String user;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startCreateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endCreateTime;

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PaymentParam{" +
                "minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", user='" + user + '\'' +
                ", startCreateTime=" + startCreateTime +
                ", endCreateTime=" + endCreateTime +
                ", id='" + getId() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", type=" + getType() +
                ", description='" + getDescription() + '\'' +
                ", createTime=" + getCreateTime() +
                ", author='" + getAuthor() + '\'' +
                ", price=" + getPrice() +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                ", state=" + getState() +
                '}';
    }
}
