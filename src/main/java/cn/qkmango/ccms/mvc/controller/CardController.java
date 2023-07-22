package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bind.CardState;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.mvc.service.CardService;
import cn.qkmango.ccms.security.holder.AccountHolder;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
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
     * 查询卡信息列表
     *
     * @param pagination 分页查询条件
     * @return 卡信息列表
     */
    @Permission(Role.admin)
    @PostMapping("pagination/list.do")
    public R<List<Card>> list(@RequestBody Pagination<Card> pagination) {
        return service.list(pagination);
    }


    /**
     * 充值
     *
     * @param account 账户
     * @param amount  充值金额
     * @return
     */
    @Permission(Role.admin)
    @PostMapping("update/recharge.do")
    public R recharge(@NotNull Integer account,
                      @NotNull @Range(min = 100, max = 100000, message = "充值金额在1~1000元") Integer amount,
                      Locale locale) throws UpdateException {
        service.recharge(account, amount, locale);
        return R.success(messageSource.getMessage("db.update.recharge.success", null, locale));
    }

    @PostMapping("update/state.do")
    public R state(Integer account, CardState state) throws UpdateException {

        Integer id = AccountHolder.getId();
        Role role = AccountHolder.getRole();
        //如果不是管理员，且不是自己的卡，就不能修改
        if (role != Role.admin && !account.equals(id)) {
            R.fail(messageSource.getMessage("db.card.update.state.failure", null, LocaleContextHolder.getLocale()));
        }

        service.state(account, state);
        return R.success(messageSource.getMessage("db.card.update.state.success", null, LocaleContextHolder.getLocale()));
    }

}
