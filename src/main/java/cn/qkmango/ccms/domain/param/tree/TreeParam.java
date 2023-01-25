package cn.qkmango.ccms.domain.param.tree;

import java.io.Serializable;
import java.util.List;

/**
 * 树型列表查询条件接口
 * <p>
 * 该接口包含当前结点的 ids 集合，以及父节点的 parentIds 集合<br>
 * 用于数据库查询 is in (ids) and {parent} in (parentIds)
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-13 14:56
 */
public interface TreeParam extends Serializable {

    /**
     * 设置自己的 id列表
     * 用于数据库查询 id in (ids)
     *
     * @param ids id列表
     */
    public void setIds(List<String> ids);

    /**
     * 获取自己的 id列表
     *
     * @return id列表
     */
    public List<String> getIds();

    /**
     * 设置父层的 id列表
     * 用于数据库查询 parentId in (parentIds)
     *
     * @param parentIds 父层id列表
     */
    public void setParentIds(List<String> parentIds);

    /**
     * 获取父层的 id列表
     *
     * @return 父层id列表
     */
    public List<String> getParentIds();
}
