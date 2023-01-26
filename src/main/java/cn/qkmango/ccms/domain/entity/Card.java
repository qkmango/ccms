package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Query;
import cn.qkmango.ccms.common.validate.group.Update;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * 卡信息
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 20:09
 */
public class Card implements Serializable {

    @NotEmpty(groups = {Update.class, Query.class,UpdateRecharge.class})
    @Pattern(regexp = "^\\d{10}$",groups = {Update.class, Query.class})
    private String user;

    @NotNull(message = "不能为空",groups = {UpdateRecharge.class})
    @Range(message = "充值金额在 1~1000 元", groups = {UpdateRecharge.class}, min = 100, max = 100000)
    private Integer balance;

    @NotNull(groups = {Update.class})
    private Boolean lock;

    public Card() {
    }

    public Card(String user, Integer balance, Boolean lock) {
        this.user = user;
        this.balance = balance;
        this.lock = lock;
    }

    public Card(String user, Integer balance) {
        this.user = user;
        this.balance = balance;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Boolean getLock() {
        return lock;
    }



    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    @Override
    public String toString() {
        return "Card{" +
                "user='" + user + '\'' +
                ", balance=" + balance +
                ", lock=" + lock +
                '}';
    }

    /**
     * 分组校验接口
     */
    public interface UpdateRecharge{}

}
