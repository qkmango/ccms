package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.util.MailTemplate;
import cn.qkmango.ccms.domain.bo.Mail;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.middleware.cache.captcha.DefaultCaptchaCache;
import cn.qkmango.ccms.middleware.mq.mail.MailSendMQSender;
import cn.qkmango.ccms.mvc.service.MailSenderService;
import cn.qkmango.ccms.security.holder.AccountHolder;
import jakarta.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
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
    private MailTemplate mailTemplate;
    @Resource(name = "captchaCache")
    private DefaultCaptchaCache captchaCache;
    @Resource
    private MailSendMQSender senderMQ;
    @Value("${spring.mail.from}")
    private String from;
    private final Logger logger = Logger.getLogger(this.getClass());


    /**
     * 发送修改邮箱验证码
     *
     * @param addr 邮箱
     */
    @Override
    public void sendCaptchaUpdateEmail(String addr) {
        Account account = AccountHolder.getAccount();
        Integer id = account.getId();
        // 生成验证码，并存入缓存
        String captcha = captchaCache.set(new String[]{id.toString(), addr});
        String content = mailTemplate.build(captcha);
        // 发送到MQ
        Mail mail = new Mail(from, addr, "CCMS 邮箱验证码", content);
        senderMQ.send(mail);
    }
}
