package cn.qkmango.ccms.security;

/**
 * 第三方登录用户信息
 * 只有基本信息 name uid avatarUrl
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-18 22:34
 */
public class UserInfo {
    //昵称
    private String name;
    //第三方平台唯一标识
    private String uid;
    //头像地址
    private String avatarUrl;
    //账号是否认证成功
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
