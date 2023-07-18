package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.database.DeleteException;
import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.domain.entity.Notice;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.mvc.dao.NoticeDao;
import cn.qkmango.ccms.mvc.service.NoticeService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Locale;

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
    private ReloadableResourceBundleMessageSource messageSource;

    /**
     * 公告分页列表
     *
     * @param pagination 分页查询条件
     * @return 分页列表
     */
    @Override
    public R<List<Notice>> list(Pagination<Notice> pagination) {
        List<Notice> noticeList = dao.list(pagination);
        int count = dao.getCount(pagination);
        return R.success(noticeList).setCount(count);
    }

    /**
     * 管理员删除公告
     *
     * @param notice 公告
     * @param locale 语言环境
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Notice notice, Locale locale) throws DeleteException {
        int affectedRows = dao.delete(notice);
        if (affectedRows != 1) {
            throw new DeleteException(messageSource.getMessage("db.deleteNotice.failure", null, locale));
        }
    }

    /**
     * 管理员发布公告
     *
     * @param notice 公告
     * @param locale 语言环境
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insert(@Validated(Insert.class) Notice notice, Locale locale) throws InsertException {
        int affectedRows = dao.insert(notice);
        if (affectedRows != 1) {
            throw new InsertException(messageSource.getMessage("db.notice.insert.failure", null, locale));
        }
    }

    /**
     * 获取公告详情
     *
     * @param notice id
     * @return 公告详情
     */
    @Override
    public Notice detail(Notice notice) {
        return dao.detail(notice);
    }
}
