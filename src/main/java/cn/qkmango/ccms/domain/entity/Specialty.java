package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.common.validate.group.Update;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 专业
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-11 11:16
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Specialty implements Serializable {

    @NotEmpty(groups = {Update.class})
    private String id;

    @NotEmpty(groups = {Insert.class})
    private String name;
    private String description;

    @NotEmpty(groups = {Insert.class})
    private String faculty;


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

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", faculty='" + faculty + '\'' +
                '}';
    }
}
