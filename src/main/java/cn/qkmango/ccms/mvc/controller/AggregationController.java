package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bind.PermissionType;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.mvc.service.AggregationService;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;

/**
 * 聚合接口
 * <p>提供整个页面的一次请求所需的数据
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-07 20:25
 */
@RestController
@RequestMapping("aggregation")
public class AggregationController {

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private AggregationService aggregationService;

    /**
     * 用户欢迎界面聚合数据
     * @param session 会话
     * @param locale 语言环境
     * @return 用户首页所需的数据
     */
    @Permission(PermissionType.user)
    @GetMapping("userWelcome.do")
    public R<Map<String, Object>> userWelcome(HttpSession session, Locale locale) {
        Account account = (Account) session.getAttribute("account");
        R<Map<String, Object>> r = aggregationService.userWelcome(account.getId());
        r.setSuccess(true);
        return r;
    }
}
