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
public interface NoticeDao {
    int insert(Notice notice);

    int delete(Notice notice);

    /**
     * 获取未分页的总记录条数
     *
     * @return 总条数
     */
    int getCount();

    int getCount(Pagination<Notice> pagination);

    /**
     * 公告分页列表
     *
     * @param pagination 分页查询条件
     * @return 分页列表
     */
    List<Notice> list(Pagination<Notice> pagination);

    /**
     * 获取公告详情
     *
     * @param notice id
     * @return 公告详情
     */
    Notice detail(Notice notice);
}
