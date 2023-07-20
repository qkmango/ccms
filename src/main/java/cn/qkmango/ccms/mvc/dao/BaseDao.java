package cn.qkmango.ccms.mvc.dao;

import org.apache.ibatis.annotations.Select;

/**
 * 基本的Dao接口
 * <p>
 * 泛型T为实体类，I为实体类的id类型
 * </p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-10 21:52
 */
public interface BaseDao<T, I> {
    /**
     * 获取未分页的总记录数
     *
     * @return 总记录数
     */
    @Select("SELECT FOUND_ROWS()")
    int count();

    /**
     * 根据id获取记录
     *
     * @param id id
     * @return 记录
     */
    T getRecordById(I id);
}
