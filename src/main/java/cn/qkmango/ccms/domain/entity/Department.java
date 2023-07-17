package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Insert;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

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

    @NotBlank(groups = {Insert.class})
    private String name;

    private String description;

    @Min(value = 0, groups = {Insert.class})
    @NotNull(groups = {Insert.class})
    private Integer parent;

    //附加信息，可以区分 年级 等等
    private String addition;

    //是否是叶子节点
    @NotNull(groups = {Insert.class})
    private Boolean leaf;

    public Boolean getLeaf() {
        return leaf;
    }

    public Department setLeaf(Boolean leaf) {
        this.leaf = leaf;
        return this;
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

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", parent=" + parent +
                ", addition='" + addition + '\'' +
                ", left=" + leaf +
                '}';
    }
}
