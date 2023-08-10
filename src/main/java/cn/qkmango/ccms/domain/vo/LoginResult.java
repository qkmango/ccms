package cn.qkmango.ccms.domain.vo;

import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.security.token.TokenEntity;

/**
 * 登陆账户返回的数据
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-10 20:36
 */
public class LoginResult {
    /**
     * 登陆成功后返回的账户信息
     */
    private Account account;
    /**
     * 登陆成功后返回的 token
     */
    private TokenEntity token;


    public LoginResult() {
    }

    public LoginResult(Account account, TokenEntity token) {
        this.account = account;
        this.token = token;
    }

    public Account getAccount() {
        return account;
    }

    public LoginResult setAccount(Account account) {
        this.account = account;
        return this;
    }

    public TokenEntity getToken() {
        return token;
    }

    public LoginResult setToken(TokenEntity token) {
        this.token = token;
        return this;
    }

    @Override
    public String toString() {
        return "LoginResp{" +
                "token=" + token +
                '}';
    }
}
