package cn.qkmango.ccms.domain.param;

import cn.qkmango.ccms.domain.bind.AccountState;
import cn.qkmango.ccms.domain.bind.CardState;
import cn.qkmango.ccms.domain.bind.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 添加账户
 * 用于接收前端传来的添加账户的参数
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-15 22:44
 */
public class AccountInsertParam {
    @NotNull
    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    private Role role;

    @NotNull
    private AccountState accountState;

    @NotNull
    private CardState cardState;

    @NotNull
    private Integer department;

    public Integer getId() {
        return id;
    }

    public AccountInsertParam setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AccountInsertParam setName(String name) {
        this.name = name;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public AccountInsertParam setRole(Role role) {
        this.role = role;
        return this;
    }

    public AccountState getAccountState() {
        return accountState;
    }

    public AccountInsertParam setAccountState(AccountState accountState) {
        this.accountState = accountState;
        return this;
    }

    public CardState getCardState() {
        return cardState;
    }

    public AccountInsertParam setCardState(CardState cardState) {
        this.cardState = cardState;
        return this;
    }

    public Integer getDepartment() {
        return department;
    }

    public AccountInsertParam setDepartment(Integer department) {
        this.department = department;
        return this;
    }

    @Override
    public String toString() {
        return "AccountInsertParam{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", accountState=" + accountState +
                ", cardState=" + cardState +
                ", department=" + department +
                '}';
    }
}
