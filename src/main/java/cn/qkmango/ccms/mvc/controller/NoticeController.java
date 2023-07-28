package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.database.DeleteException;
import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.entity.Notice;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.mvc.service.NoticeService;
import cn.qkmango.ccms.security.holder.AccountHolder;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
     * @return 插入结果
     */
    @Permission(Role.admin)
    @PostMapping("one/insert.do")
    public R<Object> insert(@Validated(Insert.class) Notice notice) throws InsertException {
        Integer account = AccountHolder.getId();
        notice.setAuthor(account);
        notice.setCreateTime(new Date());
        service.insert(notice);

        return R.success(messageSource.getMessage("db.notice.insert.success", null, LocaleContextHolder.getLocale()));
    }

    /**
     * 管理员删除公告
     *
     * @param id
     */
    @Permission(Role.admin)
    @PostMapping("one/delete.do")
    public R<Object> delete(@NotNull Integer id) throws DeleteException {
        service.delete(id);
        return R.success(messageSource.getMessage("db.deleteNotice.success", null, LocaleContextHolder.getLocale()));
    }

    /**
     * 公告分页列表
     *
     * @param pagination 分页查询条件
     * @return 分页列表
     */
    @Permission({Role.user, Role.admin})
    @PostMapping("pagination/list.do")
    public R<List<Notice>> list(@RequestBody Pagination<Notice> pagination) {
        return service.list(pagination);
    }

    /**
     * 获取公告详情
     *
     * @param id
     * @return 公告详情
     */
    @GetMapping("one/detail.do")
    public R<Notice> detail(@NotNull Integer id) {
        Notice detail = service.detail(id);
        return R.success(detail);
    }


}
