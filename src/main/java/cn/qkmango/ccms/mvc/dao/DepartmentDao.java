package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.Department;
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
public interface DepartmentDao extends BaseDao {

    /**
     * 根据id查询部门信息
     *
     * @param id 部门id
     * @return 部门信息
     */
    Department getRecordById(Integer id);


    List<Department> childList(Integer id);

    int insert(Department department);

}
