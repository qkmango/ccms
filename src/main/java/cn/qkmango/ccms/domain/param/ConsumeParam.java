package cn.qkmango.ccms.domain.param;

import cn.qkmango.ccms.domain.bind.ConsumeType;
import cn.qkmango.ccms.domain.entity.Consume;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * 消费查询的查询条件
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-21 13:10
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsumeParam extends Consume {
    /**
     * 开始时间
     * 给条件查询使用
     */
    private Date startCreateTime;

    /**
     * 结束时间
     * 给条件查询使用
     */
    private Date endCreateTime;

    /**
     * 最小消费金额
     */
    private Integer minPrice;
    /**
     * 最大消费金额
     */
    private Integer maxPrice;

    public ConsumeParam() {
    }

    public ConsumeParam(Integer id, String user, Date createTime, Integer price, ConsumeType type, String info, String pos, Date startCreateTime, Date endCreateTime, Integer minPrice, Integer maxPrice) {
        super(id, user, createTime, price, type, info, pos);
        this.startCreateTime = startCreateTime;
        this.endCreateTime = endCreateTime;
        this.minPrice = minPrice;
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

    @Override
    public String toString() {
        return "ConsumeParam{" +
                "startCreateTime=" + startCreateTime +
                ", endCreateTime=" + endCreateTime +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", id=" + getId() +
                ", user='" + getUser() + '\'' +
                ", createTime=" + getCreateTime() +
                ", price=" + getPrice() +
                ", type=" + getType() +
                ", info='" + getInfo() + '\'' +
                ", pos='" + getPos() + '\'' +
                '}';
    }
}
