package cn.qkmango.ccms.domain.vo;

import cn.qkmango.ccms.domain.entity.Area;
import cn.qkmango.ccms.domain.entity.Pos;
import cn.qkmango.ccms.domain.entity.Store;

import java.io.Serializable;

/**
 * 刷卡机VO
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-07 13:21
 */
public class PosVO implements Serializable {
    private Pos pos;
    private Store store;
    private Area area;

    public Pos getPos() {
        return pos;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "PosVO{" +
                "pos=" + pos +
                ", store=" + store +
                ", area=" + area +
                '}';
    }
}
