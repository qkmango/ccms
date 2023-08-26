package cn.qkmango.ccms.middleware.mq;

/**
 * RocketMQ 延迟等级
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-26 15:22
 */
public class MQDelayLevel {
    // 延时消息等级分为18个：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
    public static final int LEVEL_1S = 1;
    public static final int LEVEL_5S = 2;
    public static final int LEVEL_10S = 3;
    public static final int LEVEL_30S = 4;
    public static final int LEVEL_1M = 5;
    public static final int LEVEL_2M = 6;
    public static final int LEVEL_3M = 7;
    public static final int LEVEL_4M = 8;
    public static final int LEVEL_5M = 9;
    public static final int LEVEL_6M = 10;
    public static final int LEVEL_7M = 11;
    public static final int LEVEL_8M = 12;
    public static final int LEVEL_9M = 13;
    public static final int LEVEL_10M = 14;
    public static final int LEVEL_20M = 15;
    public static final int LEVEL_30M = 16;
    public static final int LEVEL_1H = 17;
    public static final int LEVEL_2H = 18;
}
