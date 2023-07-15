package cn.qkmango.ccms.domain.entity;


/**
 * 用户实体类
 * <p>
 * 继承自父类 {@link Account}
 *
 * @author qkmango
 */
public class User {

    private String id;

    private Integer account;

    private Long card;

    private String name;

    private String email;

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getAccount() {
        return account;
    }

    public User setAccount(Integer account) {
        this.account = account;
        return this;
    }

    public Long getCard() {
        return card;
    }

    public User setCard(Long card) {
        this.card = card;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", account=" + account +
                ", card=" + card +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
