package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Query.Login;
import cn.qkmango.ccms.common.validate.group.Update;
import cn.qkmango.ccms.domain.bind.AccountState;
import cn.qkmango.ccms.domain.bind.Role;
import cn.qkmango.ccms.mvc.service.CardService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * 账户
 *
 * @author qkmango
 * @date 2022-10-22 16:36
 */
public class Account implements Serializable {

    /**
     * ID
     */
    @NotEmpty(groups = {Login.class, Update.class, CardService.class})
    private String id;

    @NotNull(groups = Login.class)
    private String password;

    @NotNull(groups = Login.class)
    private Role role;

    private AccountState state;

    public Account() {
    }

    public Account(String id, @NotNull(groups = Login.class) String password, @NotNull(groups = Login.class) Role role, AccountState state) {
        this.id = id;
        this.password = password;
        this.role = role;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public AccountState getState() {
        return state;
    }

    public void setState(AccountState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", state=" + state +
                '}';
    }
}
