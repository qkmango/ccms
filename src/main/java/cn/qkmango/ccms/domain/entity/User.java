package cn.qkmango.ccms.domain.entity;


import cn.qkmango.ccms.common.validate.group.Insert;
import cn.qkmango.ccms.domain.bind.PermissionType;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

/**
 * 用户实体类
 * <p>
 * 继承自父类 {@link Account} 的属性
 * <li>protected Integer id;
 * <li>protected String password;
 * <li>protected String name;
 *
 * @author qkmango
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends Account {

    /**
     * 所属学院
     */
    private Integer faculty;
    /**
     * 所属专业
     */
    private Integer specialized;
    /**
     * 所属班级
     */
    private Integer clazz;

    /**
     * 是否注销
     */
    private Boolean unsubscribe;


    /**
     * 身份证号
     */
    @NotEmpty(groups = {Insert.class})
    @Pattern(groups = {Insert.class}, regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$")
    private String idCard;

    public User() {
    }

    public User(String id, String password, String name, PermissionType permissionType, Integer faculty, Integer specialized, Integer clazz, Boolean unsubscribe, String idCard) {
        super(id, password, name, permissionType);
        this.faculty = faculty;
        this.specialized = specialized;
        this.clazz = clazz;
        this.unsubscribe = unsubscribe;
        this.idCard = idCard;
    }

    public Integer getClazz() {
        return clazz;
    }

    public void setClazz(Integer clazz) {
        this.clazz = clazz;
    }

    public Integer getSpecialized() {
        return specialized;
    }

    public void setSpecialized(Integer specialized) {
        this.specialized = specialized;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard.toUpperCase();
    }

    public Integer getFaculty() {
        return faculty;
    }

    public void setFaculty(Integer faculty) {
        this.faculty = faculty;
    }

    public Boolean getUnsubscribe() {
        return unsubscribe;
    }

    public void setUnsubscribe(Boolean unsubscribe) {
        this.unsubscribe = unsubscribe;
    }

    @Override
    public String toString() {
        return "User{" +
                "faculty=" + faculty +
                ", specialized=" + specialized +
                ", clazz=" + clazz +
                ", unsubscribe=" + unsubscribe +
                ", idCard='" + idCard + '\'' +
                ", id='" + getId() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", name='" + getName() + '\'' +
                ", permissionType=" + getPermissionType() +
                '}';
    }
}
