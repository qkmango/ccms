package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.database.DeleteException;
import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.entity.Notice;
import cn.qkmango.ccms.domain.pagination.Pagination;

import java.util.List;

/**
 * 公告
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-07 22:52
 */
public interface NoticeService {
    /**
     * 管理员发布公告
     *
     * @param notice 公告
     */
    void insert(Notice notice) throws InsertException;

    /**
     * 管理员删除公告
     *
     * @param id
     */
    void delete(Integer id) throws DeleteException;

    /**
     * 公告分页列表
     *
     * @param pagination 分页查询条件
     * @return 分页列表
     */
    R<List<Notice>> list(Pagination<Notice> pagination);

    /**
     * 获取公告详情
     *
     * @param id
     * @return 公告详情
     */
    Notice record(Integer id);
}
