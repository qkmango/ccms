package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.domain.entity.Area;
import cn.qkmango.ccms.domain.entity.Faculty;
import cn.qkmango.ccms.domain.param.ClazzParam;
import cn.qkmango.ccms.domain.param.FacultyParam;
import cn.qkmango.ccms.domain.param.SpecialtyParam;
import cn.qkmango.ccms.domain.vo.TreeNode;
import cn.qkmango.ccms.mvc.dao.TreeDao;
import cn.qkmango.ccms.mvc.service.TreeService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 树型结构数据的接口 Service 层
 * 懒加载和预加载通过此服务层来维护<br/>
 * 当为懒加载时, 只加载本层的信息, 相当于 Dao 层查询列表(list);<br/>
 * 当为预加载时, 加载本层和下一层的信息, 此 Service 层方法内部可能会调用多个 Dao 层方法, 然后聚合为树型列表;
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-07 14:15
 */
@Service
public class TreeServiceImpl implements TreeService {

    @Resource
    private TreeDao dao;


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
    public List<TreeNode> areaLazyList() {
        return dao.areaList();
    }


    /**
     * 获取商户树型列表
     *
     * @param area 区域id
     * @return 商户树型列表
     */
    @Override
    public List<TreeNode> storeLazyListByArea(String area) {
        return dao.storeListByArea(area);
    }

    /**
     * 预加载区域和商户树型列表
     *
     * @return 区域和商户树型列表
     */
    @Override
    public List<TreeNode> storePreloadAreaAndStoreTree() {
        //获取全部的区域列表和商户列表
        List<TreeNode> areaList = dao.areaList();
        List<TreeNode> storeList = dao.storeList();

        // 遍历，通过stream流的方式，
        // 每次循环从 storeList 的 stream 流中过滤出 store.parent == area.id (area.value)的元素，
        // 最后将过滤出来的元素添加到 area.children 中
        for (TreeNode area : areaList) {
            List<TreeNode> collect = storeList.stream()
                    .filter(store -> store.getParent().equals(area.getId()))
                    .collect(Collectors.toList());
            area.setChildren(collect);
        }
        return areaList;
    }

    /**
     * 获取所有学院树型列表
     * <p>懒加载, 只加载本层, 即仅返回 {@link Faculty}的信息;
     *
     * @return 所有学院树型列表
     */
    @Override
    public List<TreeNode> facultyLazyList() {
        return dao.facultyList();
    }

    /**
     * 专业列表
     *
     * @param param 专业查询条件
     * @return 专业列表
     */
    @Override
    public List<TreeNode> specialtyLazyList(SpecialtyParam param) {
        return dao.specialtyList(param);
    }

    /**
     * 获取班级树型列表 预加载，一次加载完成
     * <p>加载包含3个层级</p>
     * <code>
     * faculty{
     * specialty{
     * class
     * }
     * }
     * </code>
     *
     * @return 班级树型列表
     */
    @Override
    public List<TreeNode> clazzPreloadTree(ClazzParam param) {

        //条件获取班级列表
        List<TreeNode> clazzList = dao.clazzList(param);
        // 如果查询结果为空直接返回
        if (clazzList.size() == 0) {
            return clazzList;
        }
        //得到素有班级的父级专业id列表 specialtyIds
        List<String> specialtyIds = clazzList.stream().map(TreeNode::getParent).distinct().collect(Collectors.toList());


        //条件获取专业列表
        SpecialtyParam specialtyParam = new SpecialtyParam(specialtyIds, null);
        List<TreeNode> specialtyList = dao.specialtyList(specialtyParam);
        //如果查询结果为空直接返回
        if (specialtyList.size() == 0) {
            return specialtyList;
        }
        //得到素有专业的父级学院id列表 facultyIds
        List<String> facultyIds = specialtyList.stream().map(TreeNode::getParent).distinct().collect(Collectors.toList());
        //根据学院 facultyIds 列表获取学院列表
        List<TreeNode> facultyList = dao.facultyList(new FacultyParam(facultyIds));
        //如果查询结果为空直接返回
        if (facultyList.size() == 0) {
            return facultyList;
        }



        //将班级列表按照专业 id 分组
        Map<String, List<TreeNode>> clazzGroup = clazzList.stream()
                .collect(Collectors.groupingBy(TreeNode::getParent));
        // 遍历专业列表，将专业列表中的专业 id 作为 key，
        // 从 clazzGroup 中获取对应的班级列表，并放到专业的 children 中
        for (TreeNode node : specialtyList) {
            List<TreeNode> treeNodes = clazzGroup.get(node.getId());
            if (treeNodes != null) {
                node.setChildren(treeNodes);
            }
        }

        //将专业列表按照学院id分组
        Map<String, List<TreeNode>> specialtyGroup = specialtyList.stream()
                .collect(Collectors.groupingBy(TreeNode::getParent));

        // 遍历学院列表，将学院列表中的学院 id 作为 key，
        // 从 specialtyGroup 中获取对应的专业列表，并放到学院的 children 中
        for (TreeNode node : facultyList) {
            List<TreeNode> treeNodes = specialtyGroup.get(node.getId());
            if (treeNodes != null) {
                node.setChildren(treeNodes);
            }
        }

        return facultyList;
    }
}
