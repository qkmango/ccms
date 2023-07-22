package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.mvc.service.CaptchaService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 验证码
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-31 20:00
 */
@Validated
@RestController
@RequestMapping("captcha")
public class CaptchaController {

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private CaptchaService service;

    /**
     * 发送修改邮箱验证码
     *
     * @param email 邮箱
     * @return 发送验证码结果
     */
    @Permission({Role.admin, Role.user})
    @GetMapping("send/change/email.do")
    public R sendChangeEmail(@NotBlank(message = "{valid.email.notBlank}")
                             @Email(message = "{valid.email.illegal}") String email) {
        service.sendChangeEmail(email);
        return R.success(messageSource.getMessage("response.email.send.success", null, LocaleContextHolder.getLocale()));
    }

}
