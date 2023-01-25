package cn.qkmango.ccms.domain.param;

import java.io.Serializable;

/**
 * 查询卡的参数
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-23 18:42
 */
public class CardInfoParam implements Serializable {
    private String id;
    private String name;
    private String idCard;
    private Boolean lock;

    public CardInfoParam() {
    }

    public CardInfoParam(String id, String name, String idCard, Boolean lock) {
        this.id = id;
        this.name = name;
        this.idCard = idCard;
        this.lock = lock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    @Override
    public String toString() {
        return "CardInfoParam{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", idCard='" + idCard + '\'' +
                ", lock=" + lock +
                '}';
    }
}
