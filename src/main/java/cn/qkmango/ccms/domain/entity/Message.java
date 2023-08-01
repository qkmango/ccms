package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Insert;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 留言
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-09 15:15
 */
public class Message implements Serializable {

    private Integer id;

    @NotEmpty(message = "留言内容不能为空", groups = {Insert.class})
    @Length(message = "留言内容长度必须 < 200", groups = {Insert.class})
    private String content;
    private Integer author;
    private Long createTime;


    public Message() {
    }

    public Message(Integer id, String content, Integer author, Long createTime) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", content='" + content + '\'' + ", author='" + author + '\'' + ", createTime=" + createTime + '}';
    }
}
