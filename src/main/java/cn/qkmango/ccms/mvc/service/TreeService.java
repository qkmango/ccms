package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.domain.entity.Area;
import cn.qkmango.ccms.domain.param.ClazzParam;
import cn.qkmango.ccms.domain.param.SpecialtyParam;
import cn.qkmango.ccms.domain.vo.TreeNode;

import java.util.List;

/**
 * 树型结构数据的接口 Service 层
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-07 14:15
 */
public interface TreeService {
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
    List<TreeNode> areaLazyList();

    /**
     * 获取商户树型列表
     *
     * @param area 区域id
     * @return 商户树型列表
     */
    List<TreeNode> storeLazyListByArea(String area);

    /**
     * 预加载区域和商户树型列表
     *
     * @return 区域和商户树型列表
     */
    List<TreeNode> storePreloadAreaAndStoreTree();

    /**
     * 获取所有学院树型列表
     * <p>懒加载, 只加载本层, 即仅返回 {@link cn.qkmango.ccms.domain.entity.Faculty}的信息;
     *
     * @return 所有学院树型列表
     */
    List<TreeNode> facultyLazyList();


    /**
     * 专业列表
     *
     * @param param 专业查询条件
     * @return 专业列表
     */
    List<TreeNode> specialtyLazyList(SpecialtyParam param);

    /**
     * 获取班级树型列表 预加载，一次加载完成
     * <p>加载包含3个层级</p>
     *  <code>
     *      faculty{
     *          specialty{
     *              class
     *          }
     *      }
     *  </code>
     * @param param 班级查询条件
     * @return 班级树型列表
     */
    List<TreeNode> clazzPreloadTree(ClazzParam param);

}
