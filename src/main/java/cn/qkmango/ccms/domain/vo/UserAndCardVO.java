package cn.qkmango.ccms.domain.vo;

import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 用户和校园卡信息的合集视图对象
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 22:43
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAndCardVO implements Serializable {

    private User user;
    private Card card;

    public UserAndCardVO(User user, Card card) {
        this.user = user;
        this.card = card;
    }

    public UserAndCardVO() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }



    @Override
    public String toString() {
        return "UserAndCardVO{" +
                "user=" + user +
                ", card=" + card +
                '}';
    }
}
