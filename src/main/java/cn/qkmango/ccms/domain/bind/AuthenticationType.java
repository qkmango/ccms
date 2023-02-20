package cn.qkmango.ccms.domain.bind;

/**
 * 认证类型
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-18 19:28
 */
public enum AuthenticationType {
    /**
     * 系统内置认证
     */
    system,

    /**
     * Gitee认证
     */
    gitee,

    /**
     * 钉钉认证
     */
    dingtalk,
}
