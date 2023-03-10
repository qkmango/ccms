package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.common.validate.group.Query;
import cn.qkmango.ccms.common.validate.group.Update;
import cn.qkmango.ccms.domain.bind.PermissionType;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.entity.User;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.CardInfoParam;
import cn.qkmango.ccms.domain.vo.UserAndCardVO;
import cn.qkmango.ccms.mvc.service.CardService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

/**
 * 管理员 卡模块
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 20:07
 */
@RestController
@RequestMapping("card")
public class CardController {

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private CardService service;


    /**
     * 开卡
     *
     * @param user   学生
     * @param locale 语言环境
     * @return 开卡结果
     * @throws InsertException 开卡失败
     */
    @Permission(PermissionType.admin)
    @PostMapping("one/insert.do")
    public R<Card> insert(@Validated({Insert.class, CardService.class}) User user, Locale locale) throws InsertException {
        Card addCard = service.insert(user, locale);
        return R.success(addCard,messageSource.getMessage("db.card.insert.success", null, locale));
    }

    /**
     * 查询卡信息列表
     *
     * @param pagination 分页查询条件
     * @return 卡信息列表
     */
    @Permission(PermissionType.admin)
    @PostMapping("pagination/list.do")
    public R<List<UserAndCardVO>> list(@RequestBody Pagination<CardInfoParam> pagination) {
        return service.list(pagination);
    }

    /**
     * 更改卡状态（挂失/解挂）
     *
     * @param card   传入卡号和状态
     * @param locale 语言环境
     * @return 更改结果
     * @throws UpdateException 更改失败
     */
    @PostMapping(value = "update/state.do")
    public R state(@Validated(Update.class) Card card, Locale locale) throws UpdateException {
        service.state(card, locale);
        return R.success(messageSource.getMessage("db.updateCardState.success", null, locale));
    }


    /**
     * 查询卡详细信息
     *
     * @param card    传入卡号
     * @param session 会话
     * @param locale  语言环境
     * @return 卡详细信息
     */
    @PostMapping(value = "one/detail.do")
    public R detail(@Validated(Query.class) Card card, HttpSession session, Locale locale) {

        //如果是学生，仅可以查询自己的卡信息
        Account account = (Account) session.getAttribute("account");
        if (account.getPermissionType() == PermissionType.user) {
            card.setUser(account.getId());
        }

        UserAndCardVO detail = service.detail(card);

        if (detail != null) {
            return R.success(detail);
        }
        return R.fail(messageSource.getMessage("db.card.detail.failure", null, locale));
    }

    /**
     * 充值
     *
     * @param card 传入卡号和充值金额
     * @return 充值结果
     */
    @Permission(PermissionType.admin)
    @PostMapping(value = "update/recharge.do")
    public R recharge(@Validated(Card.UpdateRecharge.class) Card card, Locale locale) throws UpdateException {
        service.recharge(card, locale);
        return R.success(messageSource.getMessage("db.update.recharge.success", null, locale));
    }

}
