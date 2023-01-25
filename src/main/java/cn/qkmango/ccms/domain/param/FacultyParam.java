package cn.qkmango.ccms.domain.param;

import cn.qkmango.ccms.domain.entity.Faculty;
import cn.qkmango.ccms.domain.param.tree.TreeParam;

import java.util.List;

/**
 * 学院树型列表查询条件
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-13 13:56
 */
public class FacultyParam extends Faculty implements TreeParam {
    private List<String> ids;

    public FacultyParam() {
    }

    public FacultyParam(List<String> ids) {
        this.ids = ids;
    }

    public List<String> getIds() {
        return ids;
    }

    /**
     * 设置父层的 id列表
     * 用于数据库查询 parentId in (parentIds)<br>
     * 由于学院没有父层，所以该方法不做任何操作
     *
     * @param parentIds 父层id列表
     */
    @Override
    public void setParentIds(List<String> parentIds) {
    }

    /**
     * 获取父层的 id列表<br>
     * 由于学院没有父层，所以该方法返回 null
     *
     * @return 父层id列表
     */
    @Override
    public List<String> getParentIds() {
        return null;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "FacultyParam{" +
                "ids=" + ids +
                '}';
    }
}
