package cn.qkmango.ccms.domain.auth;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-17 11:37
 */
public interface AuthRequest {
    String authorize(AuthenticationAccount authentication);
}
