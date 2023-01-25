package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Delete;
import cn.qkmango.ccms.common.validate.group.Insert;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 留言
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-09 15:15
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message implements Serializable {

    @NotNull(groups = {Delete.class}, message = "id不能为空")
    private Integer id;

    @NotEmpty(message = "留言内容不能为空", groups = {Insert.class})
    @Length(message = "留言内容长度必须 < 200", groups = {Insert.class})
    private String content;
    private String author;
    private Date createTime;


    public Message() {
    }

    public Message(Integer id, String content, String author, Date createTime) {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", content='" + content + '\'' + ", author='" + author + '\'' + ", createTime=" + createTime + '}';
    }
}
