package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.common.validate.group.Update;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;


import java.io.Serializable;

/**
 * 班级
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-12 12:24
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Clazz implements Serializable {

    @NotEmpty(groups = {Update.class})
    private String id;

    @NotEmpty(groups = {Insert.class})
    private String name;

    @NotEmpty(groups = {Insert.class})
    private String specialty;

    /**
     * 年份取值在2000-2100
     */
    @NotEmpty(groups = {Insert.class})
    @Pattern(regexp = "^2[01]00$")
    private String grade;

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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", specialty='" + specialty + '\'' +
                ", grade='" + grade + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
