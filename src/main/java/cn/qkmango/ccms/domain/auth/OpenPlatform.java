package cn.qkmango.ccms.domain.auth;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-22 16:57
 */
public class OpenPlatform {

    //系统用户ID
    private String id;

    // 平台名称
    private PlatformType type;

    // 平台绑定状态
    private Boolean state;

    // 平台绑定的账号
    private String uid;

    public OpenPlatform() {
    }

    public OpenPlatform(String id, PlatformType type, Boolean state, String uid) {
        this.id = id;
        this.type = type;
        this.state = state;
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlatformType getType() {
        return type;
    }

    public void setType(PlatformType type) {
        this.type = type;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "OpenPlatform{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", state=" + state +
                ", uid='" + uid + '\'' +
                '}';
    }
}
