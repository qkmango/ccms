package cn.qkmango.ccms.domain.auth;

/**
 * 平台类型
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-18 19:28
 */
public enum PlatformType {
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

    /**
     * 支付宝认证
     */
    alipay
}
