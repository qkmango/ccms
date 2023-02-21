package cn.qkmango.ccms.domain.param;

import java.util.Date;

/**
 * 时间范围
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-29 21:21
 */
public class DatetimeRange {
    private Date startTime;
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "DatetimeRange{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
