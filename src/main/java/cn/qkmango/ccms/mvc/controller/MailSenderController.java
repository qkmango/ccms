package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.mvc.service.MailSenderService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Email;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 邮件发送控制器
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-31 20:00
 */
@Validated
@RestController
@RequestMapping("mail")
public class MailSenderController {

    @Resource
    private ReloadableResourceBundleMessageSource ms;

    @Resource
    private MailSenderService service;

    /**
     * 发送修改邮箱验证码
     *
     * @param email 邮箱
     * @return 发送验证码结果
     */
    @Permission({Role.admin, Role.user})
    @GetMapping("captcha/update-email.do")
    public R sendCaptchaEmail(@Email String email) {
        service.sendCaptchaUpdateEmail(email);
        return R.success(ms.getMessage("response.email.send.success", null, LocaleContextHolder.getLocale()));
    }

}
