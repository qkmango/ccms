package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.Area;
import cn.qkmango.ccms.domain.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 描述
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-29 18:10
 */
@Mapper
public interface AreaDao {
    int count();

    int insert(Area area);

    int delete(String id);

    List<Area> list(Pagination<Area> pagination);

    int update(Area area);

    Area detail(String id);
}
