package cn.qkmango.ccms.domain.dto;

import cn.qkmango.ccms.domain.entity.Message;

/**
 * 查询留言的参数
 * <p>用于在 {@link Message} domain无法完整提供查询的参数时的扩充</p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-21 13:27
 */
public class MessageDto extends Message {
    private Long startCreateTime;
    private Long endCreateTime;

    public Long getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Long startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Long getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Long endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "startCreateTime=" + startCreateTime +
                ", endCreateTime=" + endCreateTime +
                ", id=" + getId() +
                ", content='" + getContent() + '\'' +
                ", author=" + getAuthor() +
                ", createTime=" + getCreateTime() +
                '}';
    }
}
