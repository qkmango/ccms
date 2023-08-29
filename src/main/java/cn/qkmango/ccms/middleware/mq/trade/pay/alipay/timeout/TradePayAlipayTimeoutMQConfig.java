package cn.qkmango.ccms.middleware.mq.trade.pay.alipay.timeout;

import cn.qkmango.ccms.middleware.mq.MQDelayLevel;

/**
 * 交易支付宝支付超时消息队列配置
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-26 16:00
 */
public class TradePayAlipayTimeoutMQConfig {
    public static final String TOPIC = "pay-trade-alipay-timeout-topic";
    public static final String GROUP = "pay-trade-alipay-timeout-consumer-group";
    public static final int DELAY_LEVEL = MQDelayLevel.LEVEL_10S;
    public static final int TIMEOUT = 3000;
}
