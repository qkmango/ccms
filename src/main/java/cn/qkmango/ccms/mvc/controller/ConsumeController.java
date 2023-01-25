package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.QueryException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.domain.bind.PermissionType;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.Consume;
import cn.qkmango.ccms.domain.entity.Pos;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.ConsumeParam;
import cn.qkmango.ccms.domain.vo.ConsumeDetailsVO;
import cn.qkmango.ccms.mvc.service.ConsumeService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Locale;

/**
 * 消费
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-29 22:29
 */

@RestController
@RequestMapping("consume")
public class ConsumeController {

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private ConsumeService service;


    /**
     * 刷卡机添加消费记录
     *
     * @param locale 语言环境
     * @return 添加结果
     */
    @Permission(PermissionType.pos)
    @PostMapping("pos/one/insert.do")
    public R<Object> insert(@Validated(Insert.class) Consume consume, HttpSession session, Locale locale) throws InsertException, QueryException {
        //设置刷卡机ID
        Pos pos = (Pos) session.getAttribute("account");
        consume.setPos(pos.getId());
        consume.setType(pos.getType());

        service.insert(consume, locale);

        return R.success(messageSource.getMessage("db.consume.insert.success", null, locale));
    }

    /**
     * 分页查询消费记录
     * 如果是管理员，查询所有消费记录；如果是学生，查询自己的消费记录
     *
     * @param pagination 分页查询条件
     * @return 消费记录
     */
    @Permission({PermissionType.admin, PermissionType.pos, PermissionType.user})
    @PostMapping("pagination/list.do")
    public R<List<Consume>> list(@RequestBody Pagination<ConsumeParam> pagination, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        //如果是学生，则只能查询自己的消费信息
        if (account.getPermissionType() == PermissionType.user) {
            ConsumeParam param = pagination.getParam();
            if (param == null) {
                param = new ConsumeParam();
                pagination.setParam(param);
            }
            param.setUser(account.getId());
        }

        return service.list(pagination);
    }


    /**
     * 查询消费记录详情
     *
     * @param consume 消费id和用户id
     * @param session 会话
     * @return 消费记录详情
     */
    @Permission({PermissionType.admin, PermissionType.pos, PermissionType.user})
    @GetMapping("one/detail.do")
    public R<ConsumeDetailsVO> detail(@Validated Consume consume, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        //如果是学生，则只能查询自己的消费信息
        if (account.getPermissionType() == PermissionType.user) {
            consume.setUser(account.getId());
        }
        ConsumeDetailsVO vo = service.detail(consume);
        return R.success(vo);
    }


}
