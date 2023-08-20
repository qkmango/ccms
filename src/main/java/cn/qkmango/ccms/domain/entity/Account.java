package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Query.Login;
import cn.qkmango.ccms.domain.BaseDomain;
import cn.qkmango.ccms.domain.bind.AccountState;
import cn.qkmango.ccms.domain.bind.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 账户
 *
 * @author qkmango
 * @date 2022-10-22 16:36
 */
public class Account extends BaseDomain<Account> {

    /**
     * ID
     */
    @NotNull(groups = {Login.class})
    private Integer id;

    @NotBlank(groups = Login.class)
    private String password;

    private String email;

    private Role role;

    private AccountState state;

    private Integer department;


    public Account() {
    }

    public Account(Integer id, String password, Role role, AccountState state, Integer department) {
        this.id = id;
        this.password = password;
        this.role = role;
        this.state = state;
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public Account setId(Integer id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", state=" + state +
                ", department=" + department +
                '}';
    }
}
