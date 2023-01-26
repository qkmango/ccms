package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.common.validate.group.Update;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

/**
 * 区域
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-29 17:58
 */
public class Area implements Serializable {
    @NotEmpty(groups = {Update.class})
    private String id;
    /**
     * 区域名称
     */
    @NotEmpty(groups = {Insert.class})
    private String name;
    /**
     * 区域描述
     */
    @NotEmpty(groups = {Insert.class})
    private String description;

    public Area() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Area{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
