package cn.qkmango.ccms.mvc.dao;

import org.apache.ibatis.annotations.Select;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-10 21:52
 */
public interface BaseDao<T> {
    @Select("SELECT FOUND_ROWS()")
    int count();
}
