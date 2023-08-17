package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.dto.TradeDto;
import cn.qkmango.ccms.domain.entity.Trade;
import cn.qkmango.ccms.domain.pagination.PageData;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.vo.TradeVO;
import cn.qkmango.ccms.mvc.service.TradeService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
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

    @Permission(Role.admin)
    @PostMapping("pagination/list.do")
    public R<PageData<Trade>> list(@RequestBody Pagination<TradeDto> pagination) {
        PageData<Trade> pageData = service.list(pagination);
        return R.success(pageData);
    }

    @Permission(Role.admin)
    @GetMapping("/one/record.do")
    public R<Trade> record(@NotNull Long id) {
        return R.success(service.record(id));
    }

    @Permission(Role.admin)
    @GetMapping("/one/detail.do")
    public R<TradeVO> detail(@NotNull Long id) {
        return R.success(service.detail(id));
    }


}
