package cn.qkmango.ccms.domain.param;

import cn.qkmango.ccms.domain.entity.Clazz;
import cn.qkmango.ccms.domain.param.tree.TreeParam;

import java.util.List;

/**
 * 班级树型列表查询条件
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-12 14:01
 */
public class ClazzParam extends Clazz implements TreeParam {

    private List<String> ids;

    /**
     * 专业id列表
     */
    private List<String> parentIds;

    /**
     * 学院id
     */
    private String faculty;

    /**
     * 起始年级
     */
    private String startGrade;

    /**
     * 结束年级
     */
    private String endGrade;

    @Override
    public List<String> getIds() {
        return ids;
    }

    @Override
    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    @Override
    public List<String> getParentIds() {
        return parentIds;
    }

    @Override
    public void setParentIds(List<String> parentIds) {
        this.parentIds = parentIds;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getStartGrade() {
        return startGrade;
    }

    public void setStartGrade(String startGrade) {
        this.startGrade = startGrade;
    }

    public String getEndGrade() {
        return endGrade;
    }

    public void setEndGrade(String endGrade) {
        this.endGrade = endGrade;
    }

    @Override
    public String toString() {
        return "ClazzParam{" +
                "ids=" + ids +
                ", parentIds=" + parentIds +
                ", faculty='" + faculty + '\'' +
                ", startGrade='" + startGrade + '\'' +
                ", endGrade='" + endGrade + '\'' +
                ", id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", specialty='" + getSpecialty() + '\'' +
                ", grade='" + getGrade() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}
