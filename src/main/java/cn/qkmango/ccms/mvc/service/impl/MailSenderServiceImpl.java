package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.util.MailTemplate;
import cn.qkmango.ccms.domain.bo.Mail;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.middleware.cache.captcha.DefaultCaptchaCache;
import cn.qkmango.ccms.middleware.mq.mail.MailSendMQSender;
import cn.qkmango.ccms.mvc.service.MailSenderService;
import cn.qkmango.ccms.security.holder.AccountHolder;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
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
    @Value("${spring.mail.from}")
    private String from;
    @Resource
    private MailTemplate mailTemplate;
    @Resource(name = "captchaCache")
    private DefaultCaptchaCache captchaCache;
    @Resource
    private MailSendMQSender senderMQ;
    @Resource
    private JavaMailSender javaMailSender;

    // private final Logger logger = Logger.getLogger(this.getClass());


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
        String content = mailTemplate.build(captcha);

        // 将消息发送到消息队列
        senderMQ.send( new Mail(from, email, "修改邮箱验证码", content));
        // try {
            // MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            // MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            // mimeMessageHelper.setFrom(from);
            // mimeMessageHelper.setTo(email);
            // mimeMessageHelper.setSubject("修改邮箱验证码");
            // mimeMessageHelper.setText(content, true);

        // } catch (MessagingException e) {
        //     logger.info("邮件构建失败");
        // }
    }
}
