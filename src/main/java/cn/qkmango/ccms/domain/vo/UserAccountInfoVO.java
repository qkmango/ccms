package cn.qkmango.ccms.domain.vo;

import cn.qkmango.ccms.domain.entity.*;

import java.io.Serializable;

/**
 * 用户信息详情页
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-18 14:43
 */
public class UserAccountInfoVO extends AccountInfoVO implements Serializable {
    private User user;
    private Card card;
    private Clazz clazz;
    private Specialty specialty;
    private Faculty faculty;

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

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return "UserAccountInfoVO{" +
                "user=" + user +
                ", card=" + card +
                ", clazz=" + clazz +
                ", specialty=" + specialty +
                ", faculty=" + faculty +
                ", account=" + getAccount() +
                '}';
    }
}
