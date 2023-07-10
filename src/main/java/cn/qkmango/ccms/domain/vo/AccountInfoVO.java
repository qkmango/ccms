package cn.qkmango.ccms.domain.vo;

import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.entity.Department;
import cn.qkmango.ccms.domain.entity.User;

import java.util.LinkedList;

/**
 * 账户详细信息
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-04-18 10:19
 */
public class AccountInfoVO {
    private Account account;
    private User user;
    private Card card;
    private LinkedList<Department> departmentChain;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public LinkedList<Department> getDepartmentChain() {
        return departmentChain;
    }

    public void setDepartmentChain(LinkedList<Department> departmentChain) {
        this.departmentChain = departmentChain;
    }

    @Override
    public String toString() {
        return "AccountInfoVO{" +
                "account=" + account +
                ", user=" + user +
                ", card=" + card +
                ", departments=" + departmentChain +
                '}';
    }
}
