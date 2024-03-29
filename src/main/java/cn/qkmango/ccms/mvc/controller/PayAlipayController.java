package cn.qkmango.ccms.mvc.controller;


import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.mvc.service.AlipayService;
import cn.qkmango.ccms.pay.AlipayNotify;
import cn.qkmango.ccms.security.holder.AccountHolder;
import com.alipay.api.AlipayApiException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 支付宝
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-24 13:57
 */
@Validated
@RestController
@RequestMapping("/pay/alipay")
public class PayAlipayController {

    @Resource
    private AlipayService service;

    @Resource
    private ReloadableResourceBundleMessageSource ms;


    /**
     * 支付接口
     * subject=xxx&traceId=xxx&amount=xxx
     *
     * @param amount 订单的总金额
     */
    @Permission(Role.user)
    @GetMapping(value = "/pay.do", produces = MediaType.TEXT_HTML_VALUE)
    public String pay(@NotNull @Min(1) Integer amount) throws AlipayApiException {
        Integer account = AccountHolder.getId();
        return service.pay(account, amount);
    }

    /**
     * 支付结果异步通知
     * 必须是POST
     */
    @PostMapping("/notify.do")
    public String notify(@RequestParam Map<String, String> param, HttpServletRequest request) throws AlipayApiException {
        AlipayNotify notify = AlipayNotify.build(param);

        boolean result = service.notify(notify, request);
        return result ? "success" : "fail";
    }

}
