package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.common.validate.group.Query;
import cn.qkmango.ccms.domain.bind.ConsumeType;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 消费
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-29 22:40
 */
public class Consume implements Serializable {

    /**
     * 消费ID
     */
    @NotBlank(groups = {Query.class})
    private Integer id;

    /**
     * 学号
     */
    @NotEmpty(groups = {Insert.class})
    @Pattern(regexp = "^\\d{10}$", groups = {Insert.class})
    private String user;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 消费金额
     */
    @NotNull(groups = {Insert.class})
    @Min(value = 0, groups = {Insert.class})
    private Integer price;


    /**
     * 消费类型
     */
    private ConsumeType type;
    /**
     * 消费信息
     */
    @NotEmpty(groups = {Insert.class})
    private String info;

    /**
     * 刷卡机ID
     */
    private String pos;


    public Consume() {
    }


    public Consume(Integer id, String user, Date createTime, Integer price, ConsumeType type, String info, String pos) {
        this.id = id;
        this.user = user;
        this.createTime = createTime;
        this.price = price;
        this.type = type;
        this.info = info;
        this.pos = pos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ConsumeType getType() {
        return type;
    }

    public void setType(ConsumeType type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }


}
