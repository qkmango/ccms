package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.exception.DeleteException;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.validate.group.Delete;
import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.Message;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.MessageParam;
import cn.qkmango.ccms.domain.vo.MessageVO;
import cn.qkmango.ccms.mvc.service.MessageService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

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
     * @param locale  语言环境
     * @return 添加结果
     * @throws InsertException 添加失败
     */
    @PostMapping("one/insert.do")
    public R insert(@Validated(Insert.class) Message message, Locale locale, HttpSession session) throws InsertException {

        Account account = (Account) session.getAttribute("account");
        message.setAuthor(account.getId());

        service.insert(message, locale);
        return R.success(messageSource.getMessage("db.message.insert.success", null, locale));
    }

    /**
     * 删除留言
     *
     * @param message 留言
     * @param session 会话
     * @param locale  语言环境
     * @return 删除结果
     * @throws DeleteException 删除失败
     */
    @PostMapping("one/delete.do")
    public R delete(@Validated(Delete.class) Message message, HttpSession session, Locale locale) throws DeleteException {

        Account account = (Account) session.getAttribute("account");
        if (account.getRole() == Role.user) {
            message.setAuthor(account.getId());
        }

        service.delete(message, locale);
        return R.success(messageSource.getMessage("db.message.delete.success", null, locale));
    }


    /**
     * 分页查询留言列表
     *
     * @param pagination 分页查询条件
     * @return 留言列表
     */
    @PostMapping("pagination/list.do")
    public R<List<MessageVO>> list(@RequestBody Pagination<MessageParam> pagination) {
        return service.list(pagination);
    }

    /**
     * 留言详情
     *
     * @param id 留言id
     * @return 留言详情
     */
    @GetMapping("one/detail.do")
    public R<MessageVO> detail(@RequestParam String id) {
        MessageVO detail = service.detail(id);
        return R.success(detail);
    }

}
