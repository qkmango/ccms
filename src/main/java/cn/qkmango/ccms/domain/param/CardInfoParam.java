package cn.qkmango.ccms.domain.param;

import cn.qkmango.ccms.domain.BaseDomain;
import cn.qkmango.ccms.domain.bind.CardState;
import io.micrometer.common.util.StringUtils;

import java.io.Serializable;

/**
 * 查询卡的参数
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-23 18:42
 */
public class CardInfoParam implements Serializable, BaseDomain {
    private String id;
    private String name;
    private CardState state;

    public CardInfoParam() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if ("".equals(id)) return;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if ("".equals(name)) return;
        this.name = name;
    }

    public CardState getState() {
        return state;
    }

    public void setState(CardState state) {
        this.state = state;
    }



    /**
     * 判断属性是否全为空
     */
    @Override
    public boolean isAllNull() {
        return StringUtils.isBlank(id) &&
                StringUtils.isBlank(name) &&
                null == state;
    }
}
