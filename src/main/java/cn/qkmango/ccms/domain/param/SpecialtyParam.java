package cn.qkmango.ccms.domain.param;

import cn.qkmango.ccms.domain.entity.Specialty;
import cn.qkmango.ccms.domain.param.tree.TreeParam;

import java.util.List;

/**
 * 专业树型列表查询条件
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-13 13:45
 */
public class SpecialtyParam extends Specialty implements TreeParam {

    /**
     * 自己的id
     */
    private List<String> ids;

    /**
     * 父层学院的id
     */
    private List<String> parentIds;

    public SpecialtyParam() {
    }

    public SpecialtyParam(List<String> ids, List<String> parentIds) {
        this.ids = ids;
        this.parentIds = parentIds;
    }

    public List<String> getParentIds() {
        return parentIds;
    }

    public void setParentIds(List<String> parentIds) {
        this.parentIds = parentIds;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }


    @Override
    public String toString() {
        return "SpecialtyParam{" +
                "ids=" + ids +
                ", parentIds=" + parentIds +
                ", id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", faculty='" + getFaculty() + '\'' +
                '}';
    }
}
