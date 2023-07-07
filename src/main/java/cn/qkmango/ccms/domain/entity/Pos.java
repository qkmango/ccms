package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.domain.bind.ConsumeType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * 刷卡机
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-30 12:29
 */
public class Pos extends Account {

    @NotEmpty(groups = {Insert.class})
    private String address;

    @NotNull(groups = {Insert.class})
    private ConsumeType type;

    @NotEmpty(groups = {Insert.class})
    private String store;

    public Pos() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ConsumeType getType() {
        return type;
    }

    public void setType(ConsumeType type) {
        this.type = type;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "Pos{" +
                "address='" + address + '\'' +
                ", type=" + type +
                ", store='" + store + '\'' +
                ", id='" + getId() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", name='" + getName() + '\'' +
                ", role=" + getRole() +
                '}';
    }
}
