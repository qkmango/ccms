package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.domain.bind.DepartmentType;

/**
 * 教学部门
 * 院系 专业 班级
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-07 11:20
 */
public class Department {
    private Integer id;
    private String name;
    private String description;
    private DepartmentType type;
    private Integer parent;
    private String grade;

    public Department() {
    }

    public Department(Integer id, String name, String description, DepartmentType type, Integer parent, String grade) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.parent = parent;
        this.grade = grade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public DepartmentType getType() {
        return type;
    }

    public void setType(DepartmentType type) {
        this.type = type;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", parent='" + parent + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}