package cn.qkmango.ccms.domain.param;

import cn.qkmango.ccms.domain.entity.Message;

import java.util.Date;

/**
 * 查询留言的参数
 * <p>用于在 {@link Message} domain无法完整提供查询的参数时的扩充</p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-21 13:27
 */
public class MessageParam extends Message {
    private Date startCreateTime;
    private Date endCreateTime;

    public MessageParam() {
    }

    public MessageParam(Integer id, String content, String author, Date createTime, Date startCreateTime, Date endCreateTime) {
        super(id, content, author, createTime);
        this.startCreateTime = startCreateTime;
        this.endCreateTime = endCreateTime;
    }

    public MessageParam(Date startCreateTime, Date endCreateTime) {
        this.startCreateTime = startCreateTime;
        this.endCreateTime = endCreateTime;
    }

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
        return "MessageParam{" +
                "startCreateTime=" + startCreateTime +
                ", endCreateTime=" + endCreateTime +
                ", id=" + getId() +
                ", content='" + getContent() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", createTime=" + getCreateTime() +
                '}';
    }
}
