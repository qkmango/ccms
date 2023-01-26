package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.common.validate.group.Update;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

/**
 * 商店
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-29 17:52
 */
public class Store implements Serializable {

    @NotEmpty(groups = {Update.class})
    private String id;
    /**
     * 商店商家名称
     */
    @NotEmpty(groups = {Insert.class})
    private String name;
    /**
     * 详细地址
     */
    @NotEmpty(groups = {Insert.class})
    private String address;
    /**
     * 所在区域ID
     */
    @NotEmpty(groups = {Insert.class})
    private String area;
    /**
     * 商店描述
     */
    private String description;

    /**
     * 管理者姓名
     */
    @NotEmpty(groups = {Insert.class})
    private String manager;
    /**
     * 管理者电话
     */
    @NotNull(groups = {Insert.class})
    @Pattern(regexp = "^1(3|4|5|6|7|8|9)\\d{9}$", groups = {Insert.class})
    private String phone;


    public Store() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", area='" + area + '\'' +
                ", description='" + description + '\'' +
                ", manager='" + manager + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
