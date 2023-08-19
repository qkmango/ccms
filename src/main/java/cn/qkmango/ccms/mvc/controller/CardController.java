package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bind.CardState;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.dto.CardRechargeDto;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.pagination.PageData;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.mvc.service.CardService;
import cn.qkmango.ccms.security.holder.AccountHolder;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * 查询卡信息列表
     *
     * @param pagination 分页查询条件
     * @return 卡信息列表
     */
    @Permission(Role.admin)
    @PostMapping("pagination/list.do")
    public R<PageData<Card>> list(@RequestBody Pagination<Card> pagination) {
        PageData<Card> page = service.list(pagination);
        return R.success(page);
    }


    /**
     * 充值
     */
    @Permission(Role.admin)
    @PostMapping("update/recharge.do")
    public R recharge(@Validated CardRechargeDto dto) throws UpdateException {
        return service.recharge(dto);
    }

    /**
     * 修改卡状态
     */
    @Permission(Role.admin)
    @PostMapping("update/state.do")
    public R state(@NotNull Integer account, @NotNull CardState state) throws UpdateException {
        service.state(account, state);
        return R.success(messageSource.getMessage("db.card.update.state.success", null, LocaleContextHolder.getLocale()));
    }

    /**
     * 修改当前登陆用户的卡状态
     */
    @Permission(Role.user)
    @PostMapping("update/current-state.do")
    public R currentState(@NotNull CardState state) throws UpdateException {
        Integer id = AccountHolder.getId();
        service.state(id, state);
        return R.success(messageSource.getMessage("db.card.update.state.success", null, LocaleContextHolder.getLocale()));
    }

    /**
     * 查询当前登陆用户的卡信息
     */
    @Permission(Role.user)
    @GetMapping("one/current-card-info.do")
    public R<Card> currentInfo() {
        Integer account = AccountHolder.getId();
        Card card = service.recordByAccount(account);
        return R.success(card);
    }

}
