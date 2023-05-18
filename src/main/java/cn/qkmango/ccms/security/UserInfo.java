package cn.qkmango.ccms.security;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-18 22:34
 */
public class UserInfo {
    private String name;
    private String uid;
    private String avatarUrl;
    private boolean state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "name='" + name + '\'' +
                ", uid='" + uid + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", state=" + state +
                '}';
    }
}
