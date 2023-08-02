package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.database.DeleteException;
import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.domain.entity.Notice;
import cn.qkmango.ccms.domain.pagination.PageData;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.mvc.dao.NoticeDao;
import cn.qkmango.ccms.mvc.service.NoticeService;
import jakarta.annotation.Resource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 公告
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-07 22:52
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeDao dao;

    @Resource
    private ReloadableResourceBundleMessageSource ms;

    /**
     * 公告分页列表
     *
     * @param pagination 分页查询条件
     * @return 分页列表
     */
    @Override
    public PageData<Notice> list(Pagination<Notice> pagination) {
        List<Notice> list = dao.list(pagination);
        int count = dao.count();
        return PageData.of(list, count);
    }

    /**
     * 管理员删除公告
     *
     * @param id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) throws DeleteException {
        int affectedRows = dao.delete(id);
        if (affectedRows != 1) {
            throw new DeleteException(ms.getMessage("db.deleteNotice.failure", null, LocaleContextHolder.getLocale()));
        }
    }

    /**
     * 管理员发布公告
     *
     * @param notice 公告
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insert(Notice notice) throws InsertException {
        int affectedRows = dao.insert(notice);
        if (affectedRows != 1) {
            throw new InsertException(ms.getMessage("db.notice.insert.failure", null, LocaleContextHolder.getLocale()));
        }
    }

    /**
     * 获取公告详情
     *
     * @param id
     * @return 公告详情
     */
    @Override
    public Notice record(Integer id) {
        return dao.getRecordById(id);
    }
}
