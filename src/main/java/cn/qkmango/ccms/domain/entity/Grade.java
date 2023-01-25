package cn.qkmango.ccms.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 年份 年级 学年
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-13 12:42
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Grade implements Serializable {
    private String name;
    private String description;

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
        return "Grade{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
