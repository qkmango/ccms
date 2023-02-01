package cn.qkmango.ccms.config;

import cn.qkmango.ccms.common.util.SnowFlake;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

/**
 * 工具类配置类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-25 14:44
 */
@Configuration
public class UtilsConfig {

    /**
     * 配置雪花算法
     *
     * @return 雪花算法
     */
    @Bean(name = "snowFlake")
    public SnowFlake snowFlake() {
        return new SnowFlake(1, 1);
    }


    /**
     * 邮件验证码模板
     *
     * @return 模板
     * @throws IOException IO异常
     */
    @Bean(name = "mailCaptchaTemplate")
    public String MailCaptchaTemplate() throws IOException {
        File file = new ClassPathResource("static/template/mail_captcha_template.html").getFile();
        return FileUtils.readFileToString(file, "UTF-8");
    }
}
