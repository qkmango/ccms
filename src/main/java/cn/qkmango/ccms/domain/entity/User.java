package cn.qkmango.ccms.domain.entity;


/**
 * 用户实体类
 *
 * @author qkmango
 */
public class User {

    private Long id;

    private Integer account;

    private Long card;

    private String name;


    public Long getId() {
        return id;
    }

    public User setId(Long id) {
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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", account=" + account +
                ", card=" + card +
                ", name='" + name + '\'' +
                '}';
    }
}
