package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Update;
import cn.qkmango.ccms.domain.BaseDomain;
import cn.qkmango.ccms.domain.bind.CardState;
import jakarta.validation.constraints.NotNull;

/**
 * 卡信息
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 20:09
 */
public class Card extends BaseDomain<Card> {

    private Long id;

    private Integer account;

    @NotNull(groups = {Update.class})
    private CardState state;

    private Integer balance;

    public Card() {
    }

    public Long getId() {
        return id;
    }

    public Card setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getAccount() {
        return account;
    }

    public Card setAccount(Integer account) {
        this.account = account;
        return this;
    }

    public CardState getState() {
        return state;
    }

    public Card setState(CardState state) {
        this.state = state;
        return this;
    }

    public Integer getBalance() {
        return balance;
    }

    public Card setBalance(Integer balance) {
        this.balance = balance;
        return this;
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

}
