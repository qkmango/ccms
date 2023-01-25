package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.DeleteException;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.validate.group.Delete;
import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.common.validate.group.Query;
import cn.qkmango.ccms.domain.bind.PermissionType;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.Notice;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.mvc.service.NoticeService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 公告
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-07 22:50
 */
@RestController
@RequestMapping("notice")
public class NoticeController {

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private NoticeService service;


    /**
     * 管理员发布公告
     *
     * @param notice 公告
     * @param locale 语言环境
     * @return 插入结果
     */
    @Permission(PermissionType.admin)
    @PostMapping("/one/insert.do")
    public R<Object> insert(@Validated(Insert.class) Notice notice, HttpSession session, Locale locale) throws InsertException {
        Account account = (Account) session.getAttribute("account");
        notice.setAuthor(account.getId());
        notice.setCreateTime(new Date());
        service.insert(notice, locale);

        return R.success(messageSource.getMessage("db.notice.insert.success", null, locale));
    }

    /**
     * 管理员删除公告
     *
     * @param notice 公告
     * @param locale 语言环境
     */
    @Permission(PermissionType.admin)
    @PostMapping("/one/delete.do")
    public R<Object> delete(@Validated(Delete.class) Notice notice, Locale locale) throws DeleteException {
        service.delete(notice, locale);

        return R.success(messageSource.getMessage("db.deleteNotice.success", null, locale));
    }

    /**
     * 公告分页列表
     *
     * @param pagination 分页查询条件
     * @return 分页列表
     */
    @Permission({PermissionType.user, PermissionType.admin})
    @PostMapping("pagination/noticeList.do")
    public R<List<Notice>> getNoticeList(@RequestBody Pagination<Notice> pagination) {
        return service.getNoticeList(pagination);
    }

    /**
     * 获取公告详情
     * @param notice id
     * @return 公告详情
     */
    @GetMapping("one/detail.do")
    public R<Notice> detail(@Validated(Query.class) Notice notice) {
        Notice detail = service.detail(notice);
        return R.success(detail);
    }


}
