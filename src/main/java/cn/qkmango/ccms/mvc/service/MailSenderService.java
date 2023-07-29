package cn.qkmango.ccms.mvc.service;

/**
 * 验证码服务接口
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-31 20:05
 */
public interface MailSenderService {

    /**
     * 发送修改邮箱验证码
     *
     * @param email  邮箱
     */
    void sendChangeEmail(String email);


}
