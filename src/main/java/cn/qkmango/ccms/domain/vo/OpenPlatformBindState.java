package cn.qkmango.ccms.domain.vo;

import cn.qkmango.ccms.domain.auth.OpenPlatform;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-22 16:57
 */
public class OpenPlatformBindState {

    /**
     * Gitee平台信息
     */
    private OpenPlatform gitee;

    /**
     * dingtalk 平台信息
     */
    private OpenPlatform dingtalk;


    public OpenPlatform getGitee() {
        return gitee;
    }

    public void setGitee(OpenPlatform gitee) {
        this.gitee = gitee;
    }

    public OpenPlatform getDingtalk() {
        return dingtalk;
    }

    public void setDingtalk(OpenPlatform dingtalk) {
        this.dingtalk = dingtalk;
    }

    @Override
    public String toString() {
        return "AuthenticationBind{" +
                "gitee=" + gitee +
                ", dingtalk=" + dingtalk +
                '}';
    }
}
