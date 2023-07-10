package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.domain.entity.Department;
import cn.qkmango.ccms.mvc.dao.DepartmentDao;
import cn.qkmango.ccms.mvc.service.DepartmentService;
import jakarta.annotation.Resource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

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

        //最大循环次数 3，防止意外情况的死循环
        byte maxLoop = 3;
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
}
