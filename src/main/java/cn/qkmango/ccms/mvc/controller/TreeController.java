package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Area;
import cn.qkmango.ccms.domain.entity.Store;
import cn.qkmango.ccms.domain.param.ClazzParam;
import cn.qkmango.ccms.domain.param.SpecialtyParam;
import cn.qkmango.ccms.domain.vo.TreeNode;
import cn.qkmango.ccms.mvc.service.TreeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 树型结构数据的接口
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-07 14:08
 */
@RestController
@RequestMapping("tree")
public class TreeController {

    @Resource
    private TreeService service;

    /**
     * 获取区域树型列表
     * <p>懒加载, 只加载本层, 即仅返回 {@link Area}的信息;
     * 返回值List的每个元素{@link TreeNode}中
     * <li>id=Area.id</li>
     * <li>title=Area.name</li>
     * </p>
     *
     * @return 区域树型列表
     */
    @GetMapping("area/lazy/list.do")
    public R<List<TreeNode>> areaLazyList() {
        List<TreeNode> treeNodeList = service.areaLazyList();
        return R.success(treeNodeList).setMeta("area");
    }

    /**
     * 获取商户树型列表
     * <p>懒加载, 只加载本层, 即仅返回 {@link Store}的信息;
     * 返回值List的每个元素{@link TreeNode}中
     * <li>value=Store.id</li>
     * <li>label=Store.name</li>
     * </p>
     *
     * @return 商户树型列表
     */
    @GetMapping("store/lazy/list.do")
    public R<List<TreeNode>> storeLazyListByArea(String area) {
        List<TreeNode> treeNodeList = service.storeLazyListByArea(area);
        return R.success(treeNodeList).setMeta("store");
    }

    /**
     * 获取商户树型列表
     * 预加载
     *
     * @return 商户树型列表
     */
    @GetMapping("store/preload/list.do")
    public R<List<TreeNode>> storePreloadAreaAndStoreTree() {
        List<TreeNode> treeNodeList = service.storePreloadAreaAndStoreTree();
        return R.success(treeNodeList);
    }

    /**
     * 获取学院列表
     *
     * @return 学院列表
     */
    @GetMapping("faculty/lazy/list.do")
    public R<List<TreeNode>> facultyLazyList() {
        List<TreeNode> treeNodeList = service.facultyLazyList();
        return R.success(treeNodeList).setMeta("faculty");
    }

    /**
     * 获取专业列表
     * 懒加载
     * 当参数 faculty!= null 时仅加载指定faculty的专业，
     * 当参数 faculty== null 时加载所有专业
     *
     * @param param 专业查询条件
     * @return 专业列表
     */
    @GetMapping("specialty/lazy/list.do")
    public R<List<TreeNode>> specialtyLazyList(SpecialtyParam param) {
        List<TreeNode> treeNodeList = service.specialtyLazyList(param);
        return R.success(treeNodeList).setMeta("specialty");
    }

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
     * @return 班级树型列表
     */
    @GetMapping("class/preload/list.do")
    public R<List<TreeNode>> clazzPreloadTree(ClazzParam param) {
        List<TreeNode> treeNodeList = service.clazzPreloadTree(param);
        return R.success(treeNodeList);
    }
}
