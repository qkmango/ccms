package cn.qkmango.ccms.domain.vo;

import cn.qkmango.ccms.domain.entity.Area;
import cn.qkmango.ccms.domain.entity.Store;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 商户和区域的展示视图对象
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-29 20:28
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreAndAreaVO implements Serializable {
    private Store store;
    private Area area;

    public StoreAndAreaVO() {
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
        return "StoreAndAreaVO{" +
                "store=" + store +
                ", area=" + area +
                '}';
    }
}
