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
     * 发送消息
     * 默认回调
     *
     * @param msg 消息
     */
    void send(T msg);

    /**
     * 发送消息
     *
     * @param msg      消息
     * @param callback 回调
     */
    void send(T msg, SendCallback callback);
}
