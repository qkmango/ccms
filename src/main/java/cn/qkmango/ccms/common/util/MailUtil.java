package cn.qkmango.ccms.common.util;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


/**
 * 邮件工具类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-31 20:05
 */
@Component
public class MailUtil {

    /**
     * 发送发邮箱地址
     */
    @Value("${spring.mail.from}")
    private String from;

    @Resource
    private JavaMailSender mailSender;

    /**
     * 发送简单邮件
     *
     * @param to      接收者邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendSimpleText(String to, String subject, String content) throws MailException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 邮件发送来源,邮件发送目标,设置标题,设置内容
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);

        // 发送
        mailSender.send(simpleMailMessage);
    }

    /**
     * 发送带HTML样式的邮件
     *
     * @param to      接收者邮箱
     * @param subject 邮件主题
     * @param html    邮件内容
     */
    public void sendWithHtml(String to, String subject, String html) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        // 邮件发送来源,邮件发送目标,设置标题,设置内容，并设置内容 html 格式为 true
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(html, true);

        // 发送
        mailSender.send(mimeMessage);
    }
}
