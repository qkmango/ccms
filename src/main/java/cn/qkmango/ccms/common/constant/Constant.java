package cn.qkmango.ccms.common.constant;

/**
 * 常量
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-24 20:05
 */
public class Constant {
    public static final String PAY_TRADE_MQ_CONSUMER_GROUP = "ccms";
    // 交易超时30分钟
    public static final int PAY_TRADE_TIMEOUT_SECOND = 30 * 60;
    // 交易超时MQ topic主题
    public static final String PAY_TRADE_TIMEOUT_MQ_TOPIC = "pay-trade-alipay-timeout";
    // 交易超时30分钟，对应MQ延迟级别16
    // 延时消息等级分为18个：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
    public static final int PAY_TRADE_TIMEOUT_MQ_DELAY_LEVEL = 16;
    // public static final int PAY_TRADE_TIMEOUT_MQ_DELAY_LEVEL = 3;

    // 关单失败重试延迟 10s
    public static final int PAY_TREAD_TIMEOUT_RETRY_MQ_DELAY_LEVEL = 3;
}
