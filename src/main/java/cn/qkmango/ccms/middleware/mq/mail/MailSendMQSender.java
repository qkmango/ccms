package cn.qkmango.ccms.middleware.mq.mail;

import cn.qkmango.ccms.domain.bo.Mail;
import cn.qkmango.ccms.middleware.mq.BaseMQSender;
import org.apache.log4j.Logger;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import static cn.qkmango.ccms.middleware.mq.mail.MailSendMQConfig.TOPIC;

/**
 * 邮件发送消息队列发送者
 * <p>将邮件信息发送到消息队列</p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-26 16:57
 */
@Component("mailSendMQSender")
public class MailSendMQSender implements BaseMQSender<Mail> {

    private final RocketMQTemplate mq;
    private final Logger logger = Logger.getLogger(getClass());

    public MailSendMQSender(RocketMQTemplate mq) {
        this.mq = mq;
    }

    @Override
    public void send(Mail mail) {
        mq.sendOneWay(TOPIC, MessageBuilder.withPayload(mail).build());
    }
}
