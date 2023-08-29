package cn.qkmango.ccms.middleware.mq;

import org.apache.rocketmq.client.producer.SendCallback;

/**
 * MQ 消息发送者
 *
 * @param <T> 发送的消息类型
 * @author qkmango
 * @version 1.0
 * @date 2023-08-26 15:21
 */
public interface BaseMQSender<T> {

    /**
     * 同步发送
     *
     * @return 发送是否成功
     */
    default boolean syncSend(T msg) {
        throw new UnsupportedOperationException("未实现该方法");
    }

    /**
     * 异步发送
     */
    default void asyncSend(T msg, SendCallback callback) {
        throw new UnsupportedOperationException("未实现该方法");
    }

    /**
     * 发送
     */
    default void send(T msg) {
        throw new UnsupportedOperationException("未实现该方法");
    }

}
