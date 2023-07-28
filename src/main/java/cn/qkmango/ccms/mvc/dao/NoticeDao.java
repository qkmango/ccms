package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.Notice;
import cn.qkmango.ccms.domain.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 公告
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-07 22:53
 */
@Mapper
public interface NoticeDao extends BaseDao<Notice, Integer> {
    int insert(Notice notice);

    /**
     * 公告分页列表
     *
     * @param pagination 分页查询条件
     * @return 分页列表
     */
    List<Notice> list(Pagination<Notice> pagination);
}
