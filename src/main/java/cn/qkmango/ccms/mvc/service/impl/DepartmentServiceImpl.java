package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.domain.entity.Department;
import cn.qkmango.ccms.mvc.dao.DepartmentDao;
import cn.qkmango.ccms.mvc.service.DepartmentService;
import jakarta.annotation.Resource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * 部门业务逻辑实现类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-10 21:46
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private ReloadableResourceBundleMessageSource ms;

    @Resource
    private DepartmentDao dao;

    @Override
    public Department getRecordById(Integer id) {
        return dao.getRecordById(id);
    }

    @Override
    public LinkedList<Department> departmentChain(Integer childId) {

        LinkedList<Department> chain = new LinkedList<>();

        Department department = null;
        // 如果子部门不为空，且子部门的父部门id不为0

        //最大循环次数 5，防止意外情况的死循环
        byte maxLoop = 5;
        byte i = 0;
        while ((department = dao.getRecordById(childId)) != null
                &&
                i++ < maxLoop) {
            chain.addFirst(department);
            childId = department.getParent();
            if (department.getParent() == 0) {
                break;
            }
        }

        return chain;
    }

    @Override
    public List<Department> childList(Integer id) {
        if (id == null) {
            id = 0;
        }
        return dao.childList(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insert(Department department, Locale locale) throws InsertException {
        Boolean leaf = department.getLeaf();
        Integer parent = department.getParent();

        //2. 获取给定的父结点对象父节点对象
        Department parentRecord = dao.getRecordById(parent);

        // 2.
        // 如果是叶子结点, 且父节点为0, 前后矛盾, 则抛出异常
        //                但是父节点不存在，则抛出异常
        //                但是所给的父节点是其实是叶子结点，则抛出异常
        // 如果不是叶子结点 并且不是根结点, (即为中间结点), 但是父节点不存在，则抛出异常
        //                 并且是根结点，但是父节点存在，则抛出异常
        //                并且父节点是叶子结点，则抛出异常
        // 非根节点, 但是父节点实际为子节点
        if (leaf && parent == 0 ||
                leaf && parentRecord == null ||
                leaf && parentRecord.getLeaf() ||
                !leaf && parent != 0 && parentRecord == null ||
                !leaf && parent == 0 && parentRecord != null ||
                (leaf || parent != 0) && parentRecord.getLeaf()
        ) {
            throw new InsertException(ms.getMessage("db.insert.department.failure", null, locale));
        }

        //3. 插入
        int affectedRows = dao.insert(department);
        if (affectedRows != 1) {
            throw new InsertException(ms.getMessage("db.insert.department.failure", null, locale));
        }
    }
}
