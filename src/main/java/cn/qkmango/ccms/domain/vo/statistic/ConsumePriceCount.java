package cn.qkmango.ccms.domain.vo.statistic;

import java.util.Date;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-29 18:02
 */
public class ConsumePriceCount {
    private Date date;
    private int price;
    private int count;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    @Override
    public String toString() {
        return "ConsumePriceCount{" +
                "date=" + date +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
