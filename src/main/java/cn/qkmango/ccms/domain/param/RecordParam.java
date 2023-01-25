package cn.qkmango.ccms.domain.param;

import cn.qkmango.ccms.domain.entity.Record;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Paid查询参数
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-16 17:33
 */
public class RecordParam extends Record {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startCreateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endCreateTime;

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    @Override
    public String toString() {
        return "RecordParam{" +
                "startCreateTime=" + startCreateTime +
                ", endCreateTime=" + endCreateTime +
                ", id='" + getId() + '\'' +
                ", payment='" + getPayment() + '\'' +
                ", user='" + getUser() + '\'' +
                ", state=" + getState() +
                ", createTime=" + getCreateTime() +
                '}';
    }
}
