package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.cache.captcha.DefaultCaptchaCache;
import cn.qkmango.ccms.common.util.MailTemplate;
import cn.qkmango.ccms.common.util.MailUtil;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.mvc.service.MailSenderService;
import cn.qkmango.ccms.security.holder.AccountHolder;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

/**
 * 验证码服务接口
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-31 20:05
 */
@Service
public class MailSenderServiceImpl implements MailSenderService {


    @Resource
    private ReloadableResourceBundleMessageSource message;

    @Resource
    private MailUtil mailUtil;

    @Resource
    private MailTemplate mailTemplate;

    @Resource(name = "captchaCache")
    private DefaultCaptchaCache captchaCache;

    /**
     * 发送修改邮箱验证码
     *
     * @param email 邮箱
     */
    @Override
    public void sendCaptchaEmail(String email) {
        Account account = AccountHolder.getAccount();
        Integer id = account.getId();

        // 生成验证码，并存入缓存
        String captcha = captchaCache.set(new String[]{id.toString(), email});
        String mailContent = mailTemplate.build(captcha);
        // 发送验证码到用户邮箱
        // 发送邮件
        try {
            mailUtil.sendWithHtml(email, "修改邮箱验证码", mailContent);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new MailSendException(message.getMessage("response.email.send.failure", null, LocaleContextHolder.getLocale()));
        }
    }
}
