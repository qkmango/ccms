package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bind.PermissionType;
import cn.qkmango.ccms.mvc.service.CaptchaService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

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
    private ReloadableResourceBundleMessageSource message;

    @Resource
    private CaptchaService service;

    /**
     * 发送修改邮箱验证码
     *
     * @param email 邮箱
     * @return 发送验证码结果
     */
    @Permission({PermissionType.admin, PermissionType.user})
    @GetMapping("send/change/email.do")
    public R sendChangeEmail(@NotBlank(message = "{valid.email.notBlank}")
                             @Email(message = "{valid.email.illegal}") String email,
                             Locale locale) {
        service.sendChangeEmail(email, locale);
        return R.success(message.getMessage("response.email.send.success", null, locale));
    }

}
