package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Update;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

/**
 * 学院
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-10 21:03
 */
public class Faculty implements Serializable {

    @NotEmpty(groups = {Update.class})
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;

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
        return "Faculty{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
