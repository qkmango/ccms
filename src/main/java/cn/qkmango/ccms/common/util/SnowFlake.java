package cn.qkmango.ccms.common.util;

import java.util.UUID;

/**
 * 雪花算法
 * <p> 经过修改调整, 以便获得更长久的使用时间</p>
 * <p> ID结构如下所示<br>
 * 0 | 000000000000000000 | 00000 | 000 | 000000000000
 * </p>
 * <p>
 * 其中, 共64位, 从左到右依次表示:
 * <ul>
 *     <li>符号位(1位):始终为0</li>
 *     <li>时间戳(43位):记录毫秒级的时间戳, 支持 278.48年</li>
 *     <li>工作机器ID(8位):标识不同的机器节点, 支持2^8=256个节点</li>
 *     <li>序列号(12位):每台机器每毫秒可生成的序列号, 支持每个节点每毫秒产生4096个ID</li>
 * </ul>
 * </p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-14 18:09
 */
public class SnowFlake {
    /**
     * 起始时间戳
     * 一经定义并投入使用后不可修改,否则可能造成ID冲突
     * 2023-01-01 00:00:00
     */
    private static final long START_TIMESTAMP = 1672502400000L;

    /**
     * 工作机器ID
     * 用于标识不同的机器节点
     */
    private final long workerId;
    /**
     * 序列号
     * 用于在同一毫秒内区分不同的ID
     */
    private long sequence;

    /**
     * 上一次时间戳
     * 上一次生成ID的时间戳
     */
    private long lastTimestamp = -1L;

    /**
     * 工作机器ID占据的位数
     * 它是一个常量，设置为8，表示工作机器ID占据8个位
     */
    private final long workerIdBits = 8L;
    /**
     * 序列号占据的位数
     * 它是一个常量, 设置为12, 表示序列号占据12个位
     */
    private final long sequenceBits = 12L;
    /**
     * 序列号的掩码
     * 用于生成序列号的范围。它是通过位运算得到的，所有位为1的掩码
     */
    private final long sequenceMask = ~(-1L << sequenceBits);

    /**
     * 时间戳的位移量
     * 它是根据{@code workerIdBits}和 {@code sequenceBits}计算得到的，用于把时间戳左移对应的位数
     */
    private final long timestampShift = workerIdBits + sequenceBits;


    /**
     * 生成随机的 {@code workerId} 的构造方法
     * 使用 UUID 计算 hashCode, 从而计算出 {@code workerId}
     */
    public SnowFlake() {
        long maxWorkerId = ~(-1L << workerIdBits);
        int hashCode = Math.abs(UUID.randomUUID().toString().hashCode());
        this.workerId = hashCode % (maxWorkerId + 1);
    }

    /**
     * 指定 {@code workerId} 的构造方法
     *
     * @param workerId
     */
    public SnowFlake(long workerId) {
        long maxWorkerId = ~(-1L << workerIdBits);
        if (workerId < 0 || workerId > maxWorkerId) {
            throw new IllegalArgumentException("Worker ID must be between 0 and " + maxWorkerId);
        }
        this.workerId = workerId;
    }


    public synchronized long nextId() {
        long timestamp = getCurrentTimestamp();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID.");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - START_TIMESTAMP) << timestampShift) |
                (workerId << sequenceBits) |
                sequence;
    }

    private long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = getCurrentTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTimestamp();
        }
        return timestamp;
    }
}