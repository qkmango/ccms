package cn.qkmango.ccms.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * 树型结构
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-03 18:45
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeNode implements Serializable {
    private String id;
    private String title;
    private String parent;
    private List<TreeNode> children;

    /**
     * 附加属性字段
     * 当基本的属性 id、title、parent 无法满足需求时, 可以使用该字段
     */
    private String addition;


    public TreeNode() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
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
        return "TreeNode{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", parent='" + parent + '\'' +
                ", children=" + children +
                ", addition='" + addition + '\'' +
                '}';
    }
}

