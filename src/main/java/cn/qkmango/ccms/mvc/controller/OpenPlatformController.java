package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.entity.OpenPlatform;
import cn.qkmango.ccms.mvc.service.OpenPlatformService;
import cn.qkmango.ccms.security.holder.AccountHolder;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 开放平台
 * <p>第三方开放平台</p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-25 18:02
 */
@RestController
@RequestMapping("open-platform")
public class OpenPlatformController {
    @Resource
    private ReloadableResourceBundleMessageSource ms;

    @Resource
    private OpenPlatformService service;

    /**
     * 获取开放平台绑定状态
     *
     * @return 返回已经绑定的开放平台列表
     */
    @GetMapping("list/state.do")
    public R<List> state() {
        Integer id = AccountHolder.getId();
        List<OpenPlatform> list = service.state(id);
        return R.success(list);
    }

    /**
     * 解绑开放平台
     *
     * @param type 开放平台类型
     * @return
     */
    @Permission({Role.admin, Role.user})
    @PostMapping("update/unbind.do")
    public R unbind(@NotNull PlatformType type) {
        Integer account = AccountHolder.getId();
        service.unbind(account, type);
        return R.success(ms.getMessage("db.update.open-platform.unbind.success", null, LocaleContextHolder.getLocale()));
    }

    /**
     * 绑定开放平台
     *
     * @return
     */
    @Permission({Role.admin, Role.user})
    @PostMapping("update/bind.do")
    public R bind(@NotNull String accessCode) {
        Integer account = AccountHolder.getId();
        service.bind(account, accessCode);
        return R.success(ms.getMessage("db.update.open-platform.bind.success", null, LocaleContextHolder.getLocale()));
    }


}
