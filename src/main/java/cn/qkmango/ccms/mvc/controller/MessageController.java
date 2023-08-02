package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.database.DeleteException;
import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.dto.MessageDto;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.Message;
import cn.qkmango.ccms.domain.pagination.Flow;
import cn.qkmango.ccms.domain.pagination.PageData;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.vo.MessageVO;
import cn.qkmango.ccms.mvc.service.MessageService;
import cn.qkmango.ccms.security.holder.AccountHolder;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 留言
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-09 15:19
 */
@RestController
@RequestMapping("message")
public class MessageController {

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private MessageService service;

    /**
     * 添加留言
     *
     * @param message 留言
     * @return 添加结果
     * @throws InsertException 添加失败
     */
    @PostMapping("one/insert.do")
    public R insert(@Validated(Insert.class) Message message) throws InsertException {
        message.setAuthor(AccountHolder.getId());
        service.insert(message);
        return R.success(messageSource.getMessage("db.message.insert.success", null, LocaleContextHolder.getLocale()));
    }

    /**
     * 删除留言
     * 如果是管理员，可以删除任意留言，如果是用户，只能删除自己的留言
     *
     * @param id 留言id
     */
    @PostMapping("one/delete.do")
    @Permission({Role.admin, Role.user})
    public R delete(@NotNull Integer id) throws DeleteException {
        Account account = AccountHolder.getAccount();
        Role role = account.getRole();
        Integer author = null;
        switch (role) {
            case user -> author = account.getId();
        }
        service.delete(id, author);
        return R.success(messageSource.getMessage("db.message.delete.success", null, LocaleContextHolder.getLocale()));
    }


    /**
     * 分页查询留言列表
     * <p>此分页查询仅供管理员使用，用户的分页查询请使用流式分页查询{@link cn.qkmango.ccms.mvc.controller.MessageController#list(Flow)}</p>
     */
    @Permission(Role.admin)
    @PostMapping("pagination/list.do")
    public R<PageData<MessageVO>> list(@RequestBody Pagination<MessageDto> pagination) {
        PageData<MessageVO> page = service.list(pagination);
        return R.success(page);
    }

    /**
     * 流式分页查询留言列表
     * 查询条件 last 必填
     *
     * @param flow 分页查询条件
     * @return 留言列表
     */
    @Permission(Role.user)
    @PostMapping("flow/list.do")
    public R<List<Message>> list(@RequestBody @Validated Flow<MessageDto> flow) {
        List<Message> list = service.list(flow);
        return R.success(list);
    }

    /**
     * 留言详情
     *
     * @param id 留言id
     * @return 留言详情
     */
    @GetMapping("one/record.do")
    public R<Message> record(@RequestParam Integer id) {
        Message record = service.record(id);
        return R.success(record);
    }

}
