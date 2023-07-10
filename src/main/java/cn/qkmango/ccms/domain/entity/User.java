package cn.qkmango.ccms.domain.entity;


/**
 * 用户实体类
 * <p>
 * 继承自父类 {@link Account}
 *
 * @author qkmango
 */
public class User extends Account {

    private String userId;

    private Integer card;

    private String name;

    private String email;

    private Integer department;


    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getCard() {
        return card;
    }

    public void setCard(Integer card) {
        this.card = card;
    }



    @Override
    public String toString() {
        return "User{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department=" + department +
                '}';
    }
}
