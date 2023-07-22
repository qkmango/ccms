package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Delete;
import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.common.validate.group.Query;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-07 22:45
 */
public class Notice implements Serializable {
    @NotNull(groups = {Delete.class, Query.class})
    private Integer id;

    @NotEmpty(groups = {Insert.class})
    private String title;

    @NotEmpty(groups = {Insert.class})
    private String content;

    private Integer author;
    private Date createTime;



    public Notice() {
    }

    public Notice(Integer id, String title, String content, Integer author, Date createTime) {
        this.id = id;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
