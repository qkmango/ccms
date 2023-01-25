package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.Area;
import cn.qkmango.ccms.domain.param.ClazzParam;
import cn.qkmango.ccms.domain.param.FacultyParam;
import cn.qkmango.ccms.domain.param.SpecialtyParam;
import cn.qkmango.ccms.domain.vo.TreeNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 树型结构数据的接口 Dao 层
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-07 14:16
 */
@Mapper
public interface TreeDao {
    /**
     * 获取区域树型列表
     * <p>懒加载, 只加载本层, 即仅返回 {@link Area}的信息;
     * 返回值List的每个元素{@link TreeNode}中
     * <li>value=Area.id</li>
     * <li>label=Area.name</li>
     * </p>
     *
     * @return 区域树型列表
     */
    List<TreeNode> areaList();

    /**
     * 获取商户树型列表
     *
     * @param area 区域id
     * @return 商户树型列表
     */
    List<TreeNode> storeListByArea(String area);
    List<TreeNode> storeList();

    List<TreeNode> clazzList();
    List<TreeNode> clazzList(@Param("param") ClazzParam param);

    List<TreeNode> specialtyList();
    List<TreeNode> specialtyList(@Param("param") SpecialtyParam param);

    List<TreeNode> facultyList();
    List<TreeNode> facultyList(@Param("param")FacultyParam param);

}
