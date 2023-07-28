package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Delete;
import cn.qkmango.ccms.common.validate.group.Insert;
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
    @NotNull(groups = {Delete.class})
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

    public Notice setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Notice setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Notice setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getAuthor() {
        return author;
    }

    public Notice setAuthor(Integer author) {
        this.author = author;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Notice setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
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
