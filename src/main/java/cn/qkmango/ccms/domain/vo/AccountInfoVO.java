package cn.qkmango.ccms.domain.vo;

import cn.qkmango.ccms.domain.entity.Account;

/**
 * 账户详细信息
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-04-18 10:19
 */
public class AccountInfoVO {
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "AccountInfoVO{" +
                "account=" + account +
                '}';
    }
}
