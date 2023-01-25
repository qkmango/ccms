package cn.qkmango.ccms.domain.vo;

import cn.qkmango.ccms.domain.entity.Message;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

/**
 * 留言展示视图对象
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-17 13:19
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageVO extends Message implements Serializable {
    private String authorName;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public MessageVO() {
    }

    public MessageVO(Integer id, String content, String author, Date createTime, String authorName) {
        super(id, content, author, createTime);
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "MessageVO{" +
                "authorName='" + authorName + '\'' +
                ", id=" + getId() +
                ", content='" + getContent() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", createTime=" + getCreateTime() +
                '}';
    }
}
