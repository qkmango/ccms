package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.domain.bind.ConsumeStatisticType;
import cn.qkmango.ccms.domain.bind.ConsumeType;

import java.util.Date;

/**
 * 消费统计
 * 分类按天
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-05 18:43
 */
public class ConsumeStatistic {
    private String id;
    private ConsumeType consumeType;
    private Integer price;
    private Date date;
    private Integer count;
    private ConsumeStatisticType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ConsumeType getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(ConsumeType consumeType) {
        this.consumeType = consumeType;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ConsumeStatisticType getType() {
        return type;
    }

    public void setType(ConsumeStatisticType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ConsumeStatistic{" +
                "id='" + id + '\'' +
                ", consumeType=" + consumeType +
                ", price=" + price +
                ", date=" + date +
                ", count=" + count +
                ", type=" + type +
                '}';
    }
}
