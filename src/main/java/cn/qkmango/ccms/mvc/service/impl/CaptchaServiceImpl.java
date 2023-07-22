package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.util.CaptchaUtil;
import cn.qkmango.ccms.common.util.EmailUtil;
import cn.qkmango.ccms.common.util.RedisUtil;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.mvc.service.CaptchaService;
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
public class CaptchaServiceImpl implements CaptchaService {


    @Resource
    private ReloadableResourceBundleMessageSource message;

    @Resource
    private EmailUtil emailUtil2;

    @Resource
    private String mailCaptchaTemplate;

    @Resource(name = "redisUtil")
    private RedisUtil redis;

    /**
     * 发送修改邮箱验证码
     *
     * @param email  邮箱
     * @param locale 语言环境
     */
    @Override
    public void sendChangeEmail(String email) {
        Account account = AccountHolder.getAccount();

        //获取用户id和用户类型
        Integer id = account.getId();
        Role type = account.getRole();

        //生成验证码
        String captcha = CaptchaUtil.generate();
        //生成redis key
        String key = String.format("captcha:change:email:%s:%s:%s", type, id, email);

        //发送验证码到用户邮箱
        String content = String.format(mailCaptchaTemplate, id, captcha);

        //发送邮件
        try {
            emailUtil2.sendWithHtml(email, "修改邮箱验证码", content);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new MailSendException(message.getMessage("response.email.send.failure", null, LocaleContextHolder.getLocale()));
        }
        //将验证码存入redis,并设置过期时间5分钟
        redis.set(key, captcha, 5 * 60);
    }
}
