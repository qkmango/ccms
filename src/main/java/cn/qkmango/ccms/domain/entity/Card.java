package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Query;
import cn.qkmango.ccms.common.validate.group.Update;
import cn.qkmango.ccms.domain.bind.CardState;
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

//    @NotEmpty(groups = {Update.class, Query.class, UpdateRecharge.class})
//    @Pattern(regexp = "^\\d{10}$", groups = {Update.class, Query.class})
//    private String user;

    private String id;

    private String account;

    @NotNull(groups = {Update.class})
    private CardState state;

    @NotNull(message = "不能为空", groups = {UpdateRecharge.class})
    @Range(message = "充值金额在 1~1000 元", groups = {UpdateRecharge.class}, min = 100, max = 100000)
    private Integer balance;


    public Card() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public CardState getState() {
        return state;
    }

    public void setState(CardState state) {
        this.state = state;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ", \"account\":\"" + account + '\"' +
                ", \"state\":\"" + state + '\"' +
                ", \"balance\":" + balance +
                '}';
    }

    /**
     * 分组校验接口
     */
    public interface UpdateRecharge {
    }

}
