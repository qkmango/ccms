package cn.qkmango.ccms.mvc.service;

import java.util.Locale;

/**
 * 验证码服务接口
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-31 20:05
 */
public interface CaptchaService {

    /**
     * 发送修改邮箱验证码
     *
     * @param email  邮箱
     * @param locale 语言环境
     */
    void sendChangeEmail(String email, Locale locale);


}
