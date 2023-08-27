package cn.qkmango.ccms.middleware.mq.mail;

import cn.qkmango.ccms.domain.bo.Mail;
import cn.qkmango.ccms.middleware.mq.BaseMQSender;
import org.apache.log4j.Logger;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

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
    private final SendCallback CALLBACK = new SendCallbackImpl();
    private final Logger logger = Logger.getLogger(this.getClass());

    public MailSendMQSender(RocketMQTemplate mq) {
        this.mq = mq;
    }

    @Override
    public void send(Mail msg) {
        System.out.println("Mail发送" + msg.getContent());
        // mq.convertAndSend(MailSendMQConfig.TOPIC, msg);
        mq.asyncSend(MailSendMQConfig.TOPIC, MessageBuilder.withPayload(msg).build(), CALLBACK, 3000);
    }

    @Override
    public void send(Mail msg, SendCallback callback) {
        logger.error("不支持的方法调用");
    }

    /**
     * 发送回调
     */
    private class SendCallbackImpl implements SendCallback {
        @Override
        public void onSuccess(SendResult sendResult) {
            // System.out.println("Mail 发送成功");
            logger.info(sendResult);
        }

        @Override
        public void onException(Throwable throwable) {
            // System.out.println("Mail 发送成功");
            logger.warn(throwable.getMessage());
        }
    }
}
