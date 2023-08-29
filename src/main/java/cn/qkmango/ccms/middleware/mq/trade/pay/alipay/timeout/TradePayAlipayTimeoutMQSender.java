package cn.qkmango.ccms.middleware.mq.trade.pay.alipay.timeout;

import cn.qkmango.ccms.middleware.mq.BaseMQSender;
import org.apache.log4j.Logger;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import static cn.qkmango.ccms.middleware.mq.trade.pay.alipay.timeout.TradePayAlipayTimeoutMQConfig.*;

/**
 * 交易支付订单超时发送（支付宝）
 * 将交易信息发送到延迟消息队列
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-26 15:29
 */
@Component("tradePayAlipayTimeoutMQSender")
public class TradePayAlipayTimeoutMQSender implements BaseMQSender<Long> {

    private final RocketMQTemplate mq;
    private final Logger logger = Logger.getLogger(this.getClass());
    private final SendCallback CALLBACK = new SendCallbackImpl();

    public TradePayAlipayTimeoutMQSender(RocketMQTemplate mq) {
        this.mq = mq;
    }

    @Override
    public boolean syncSend(Long msg) {
        SendResult result = mq.syncSend(TOPIC, MessageBuilder.withPayload(msg).build(), TIMEOUT, DELAY_LEVEL);
        return result.getSendStatus() == SendStatus.SEND_OK;
    }


    /**
     * 发送回调
     */
    private class SendCallbackImpl implements SendCallback {
        @Override
        public void onSuccess(SendResult sendResult) {
            logger.info(sendResult);
        }

        @Override
        public void onException(Throwable throwable) {
            logger.error(throwable.getMessage());
        }
    }
}
