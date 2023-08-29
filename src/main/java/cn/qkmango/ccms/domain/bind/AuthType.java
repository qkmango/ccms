package cn.qkmango.ccms.domain.bind;

/**
 * 授权方式
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-29 16:03
 */
public enum AuthType {
    /**
     * 通过 cookie 授权
     * 返回认证后的token，用于后续的请求，请求时在Cookie中携带token
     */
    COOKIE,
    /**
     * 通过授权码授权
     * 返回认证后的token，用于后续的请求，请求时在Header中携带token
     */
    ACCESS_TOKEN,
}
