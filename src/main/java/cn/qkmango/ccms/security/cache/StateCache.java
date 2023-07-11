package cn.qkmango.ccms.security.cache;

/**
 * state 缓存
 *
 * <p>
 * OAuth 2.0的State参数是一个随机生成的字符串, 用于在授权流程中保持状态的一致性,
 * 它的作用是为了防止跨站请求伪造（CSRF）攻击
 * </p>
 * <ul>
 * <li>在OAuth 2.0授权流程中, 当用户点击授权请求后, 授权服务器将会将State参数原封不动地返回给客户端</li>
 * <li>客户端在收到授权服务器的响应后, 会比对返回的State参数和之前发送的State参数是否一致</li>
 * <li>如果返回的State参数和之前发送的State参数不一致, 那么很可能是中间被篡改, 客户端应该立即终止授权流程, 以防止安全风险</li>
 * </ul>
 * <p>
 * 通过使用State参数, 客户端可以确保服务器返回的授权响应是针对客户端所发起的请求, 从而提高了授权流程的安全性
 * </p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-19 9:50
 */
public interface StateCache {

    /**
     * 保存 state
     *
     * @param key   key
     * @param value value
     */
    void setState(String key, String value);

    /**
     * 获取 state
     *
     * @return state
     */
    String getState(String key);

    /**
     * 删除 state
     *
     * @param key key
     */
    void deleteState(String key);
}
