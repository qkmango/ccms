package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.dto.TradeQueryDto;
import cn.qkmango.ccms.domain.dto.TradeRefundDto;
import cn.qkmango.ccms.domain.entity.Trade;
import cn.qkmango.ccms.domain.pagination.PageData;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.vo.TradeVO;
import cn.qkmango.ccms.mvc.service.TradeService;
import cn.qkmango.ccms.security.holder.AccountHolder;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 交易
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-22 13:43
 */
@Validated
@RestController
@RequestMapping("/trade")
public class TradeController {

    @Resource
    private ReloadableResourceBundleMessageSource ms;

    @Resource
    private TradeService service;

    /**
     * 分页
     * 如果是admin，可以查看所有交易记录
     * 如果是user，只能查看自己的交易记录
     * 如果是pos，只能查看自己创建的交易记录
     */
    @PostMapping("pagination/list.do")
    public R<PageData<Trade>> list(@RequestBody Pagination<TradeQueryDto> pagination) {
        Role role = AccountHolder.getRole();
        Integer account = AccountHolder.getId();
        TradeQueryDto param = pagination.getParam();

        if (param == null) {
            param = new TradeQueryDto();
            pagination.setParam(param);
        }

        switch (role) {
            case user -> param.setAccount(account);
            case pos -> param.setCreator(account);
        }
        PageData<Trade> pageData = service.list(pagination);
        return R.success(pageData);
    }

    /**
     * 信息
     */
    @Permission(Role.admin)
    @GetMapping("/one/record.do")
    public R<Trade> record(@NotNull Long id) {
        Trade record = service.record(id);
        return record == null ?
                R.fail(ms.getMessage("response.no-record", null, LocaleContextHolder.getLocale())) :
                R.success(record);
    }

    /**
     * 交易详情
     */
    @Permission(Role.admin)
    @GetMapping("/one/detail.do")
    public R<TradeVO> detail(@NotNull Long id) {
        TradeVO detail = service.detail(id);
        return detail == null ?
                R.fail(ms.getMessage("response.no-record", null, LocaleContextHolder.getLocale())) :
                R.success(detail);
    }

    /**
     * 退款
     */
    @Permission({Role.admin, Role.pos})
    @PostMapping("/update/refund.do")
    public R refund(@Validated TradeRefundDto dto) {
        // 如果是POS，则只能退款自己创建的交易
        Role role = AccountHolder.getRole();
        if (role == Role.pos) {
            dto.setCreator(AccountHolder.getId());
        }
        return service.refund(dto);
    }





}
