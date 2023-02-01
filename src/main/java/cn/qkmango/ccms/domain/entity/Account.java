package cn.qkmango.ccms.domain.entity;

import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.common.validate.group.Query.Login;
import cn.qkmango.ccms.common.validate.group.Update;
import cn.qkmango.ccms.domain.bind.PermissionType;
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

    @NotEmpty(groups = {Insert.class}, message = "名不能为空")
    private String name;

    @NotNull(groups = Login.class)
    private PermissionType permissionType;

    private String email;

    public Account() {
    }

    public Account(String id, String password, String name, PermissionType permissionType, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.permissionType = permissionType;
        this.email = email;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PermissionType getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
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
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", permissionType=" + permissionType +
                ", email='" + email + '\'' +
                '}';
    }
}
