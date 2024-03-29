package cn.qkmango.ccms.security.client;

import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.security.AuthenticationResult;
import cn.qkmango.ccms.security.UserInfo;
import cn.qkmango.ccms.security.config.AppConfig;

/**
 * 认证客户端
 * 用于获取第三方平台的认证地址、AccessToken、用户信息
 * 通过不同的实现类, 可以实现不同平台的认证, 比如：钉钉、Gitee、Github、微信等
 * 为了适配不同的平台, 方法采用可变参数, 需要实现不同的实现类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-18 21:34
 */
public interface AuthHttpClient {

    /**
     * 生成第三方平台认证地址
     *
     * @param authAccount 授权账户
     * @return 授权地址
     */
    String authorize(AuthenticationAccount authAccount, String state);


    /**
     * AccessToken
     *
     * @param params 参数
     * @return AccessToken
     */
    String accessToken(Object... params);

    /**
     * 获取用户信息
     *
     * @param params 参数
     * @return 用户信息
     */
    UserInfo userInfo(Object... params);


    /**
     * 认证
     *
     * @param state  用于防止CSRF攻击
     * @param code   用于获取AccessToken
     * @param params 参数
     * @return 认证结果
     */
    AuthenticationResult authentication(String state, String code, Object... params);


    /**
     * 获取配置信息
     *
     * @return 配置信息
     */
    AppConfig getAppConfig();

}
