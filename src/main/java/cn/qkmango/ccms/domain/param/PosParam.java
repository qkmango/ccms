package cn.qkmango.ccms.domain.param;

import cn.qkmango.ccms.domain.entity.Pos;

/**
 * 刷卡机查询参数
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-07 13:30
 */
public class PosParam extends Pos {
    private String area;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "PosParam{" +
                "area='" + area + '\'' +
                ", address='" + getAddress() + '\'' +
                ", type=" + getType() +
                ", store='" + getStore() + '\'' +
                ", id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                '}';
    }
}
