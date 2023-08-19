package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.domain.bind.DepartmentType;
import cn.qkmango.ccms.domain.entity.Department;
import cn.qkmango.ccms.domain.pagination.PageData;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.mvc.dao.DepartmentDao;
import cn.qkmango.ccms.mvc.service.DepartmentService;
import jakarta.annotation.Resource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

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
    public Department record(Integer id) {
        return dao.getRecordById(id);
    }

    @Override
    public LinkedList<Department> departmentChain(Integer childId) {

        LinkedList<Department> chain = new LinkedList<>();

        Department department;
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
        return dao.childList(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insert(Department department) throws InsertException {
        DepartmentType type = department.getType();
        Integer parent = department.getParent();

        //2. 获取给定的父结点对象父节点对象
        Department parentRecord = dao.getRecordById(parent);

        /* 2.
            leaf|middle(!root) , 且parent为0, 前后矛盾, 则抛出异常
            leaf|middle(!root) , 且parent不存在, 则抛出异常
            lead|middle(!root) , 且parent为叶子结点, 则抛出异常
            root , 且 parent != 0, 前后矛盾, 则抛出异常
         */
        if (type != DepartmentType.root && parent == 0 ||
                type != DepartmentType.root && parentRecord == null ||
                type != DepartmentType.root && parentRecord.getType() == DepartmentType.leaf ||
                type == DepartmentType.root && parent != 0
        ) {
            throw new InsertException(ms.getMessage("db.insert.department.failure", null, LocaleContextHolder.getLocale()));
        }

        //3. 插入
        int affectedRows = dao.insert(department);
        if (affectedRows != 1) {
            throw new InsertException(ms.getMessage("db.insert.department.failure", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public PageData<Department> list(Pagination<Department> pagination) {
        List<Department> list = dao.list(pagination);
        int count = dao.count();
        return PageData.of(list, count);
    }
}
