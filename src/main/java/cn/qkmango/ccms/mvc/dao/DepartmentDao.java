package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.Department;
import cn.qkmango.ccms.domain.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-10 21:48
 */
@Mapper
public interface DepartmentDao extends BaseDao<Department, Integer> {

    /**
     * 根据id查询部门信息
     *
     * @param id 部门id
     * @return 部门信息
     */
    @Override
    Department getRecordById(Integer id);

    List<Department> childList(Integer id);

    int insert(Department department);

    List<Department> list(Pagination<Department> pagination);
}
