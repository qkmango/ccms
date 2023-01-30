package cn.qkmango.ccms.domain.vo.statistic;

import cn.qkmango.ccms.domain.bind.ConsumeType;

/**
 * 消费金额统计
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-29 18:02
 */
public class ConsumePriceCount {
    private String date;
    private int price;
    private int count;
    private ConsumeType type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ConsumeType getType() {
        return type;
    }

    public void setType(ConsumeType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ConsumePriceCount{" +
                "date='" + date + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", type=" + type +
                '}';
    }
}
