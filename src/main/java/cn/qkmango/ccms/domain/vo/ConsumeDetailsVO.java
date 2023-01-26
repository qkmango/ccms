package cn.qkmango.ccms.domain.vo;

import cn.qkmango.ccms.domain.entity.Consume;
import cn.qkmango.ccms.domain.entity.Pos;
import cn.qkmango.ccms.domain.entity.User;

import java.io.Serializable;

/**
 * 消费详情展示视图对象
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-20 16:52
 */
public class ConsumeDetailsVO implements Serializable {
    private User user;
    private Consume consume;
    private Pos pos;

    public ConsumeDetailsVO() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Consume getConsume() {
        return consume;
    }

    public void setConsume(Consume consume) {
        this.consume = consume;
    }

    public Pos getPos() {
        return pos;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "ConsumeDetailsVO{" +
                "user=" + user +
                ", consume=" + consume +
                ", pos=" + pos +
                '}';
    }
}
