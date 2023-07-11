package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.domain.entity.Department;

import java.util.LinkedList;

/**
 * 部门业务逻辑接口
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-10 21:44
 */
public interface DepartmentService {
    /**
     * 根据id查询部门信息
     *
     * @param id
     * @return
     */
    Department getRecordById(Integer id);

    /**
     * 根据子部门id查询部门链
     * 通过子部门id，查询到该部门的所有父部门
     *
     * @param childId 终端子部门id
     * @return
     */
    LinkedList<Department> departmentChain(Integer childId);
}