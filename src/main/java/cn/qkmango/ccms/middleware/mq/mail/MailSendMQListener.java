package cn.qkmango.ccms.middleware.mq.mail;

import cn.qkmango.ccms.domain.bo.Mail;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * 邮件发送监听
 * <p>监听邮件发送消息队列，接收消息并发送邮件</p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-26 16:57
 */
@Component
@RocketMQMessageListener(consumerGroup = MailSendMQConfig.GROUP, topic = MailSendMQConfig.TOPIC)
public class MailSendMQListener implements RocketMQListener<Mail> {
    private final Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private JavaMailSender javaMailSender;

    @Override
    public void onMessage(Mail mail) {
        System.out.println("Mail 消费消息");
        // 将消息发送到消息队列
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(mail.getFrom());
            mimeMessageHelper.setTo(mail.getTo());
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setText(mail.getContent(), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.info("邮件构建失败");
        }


    }
}
