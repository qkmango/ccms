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

    @NotNull(groups = {Login.class})
    private Role role;

    private AccountState state;

    private Integer department;


    public Account() {
    }

    public Account(String id, String password, Role role, AccountState state, Integer department) {
        this.id = id;
        this.password = password;
        this.role = role;
        this.state = state;
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public Account setId(String id) {
        this.id = id;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Account setPassword(String password) {
        this.password = password;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public Account setRole(Role role) {
        this.role = role;
        return this;
    }

    public AccountState getState() {
        return state;
    }

    public Account setState(AccountState state) {
        this.state = state;
        return this;
    }

    public Integer getDepartment() {
        return department;
    }

    public Account setDepartment(Integer department) {
        this.department = department;
        return this;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", state=" + state +
                ", department=" + department +
                '}';
    }
}
