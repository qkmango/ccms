package cn.qkmango.ccms.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

/**
 * Bean 配置类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-02 17:32
 */
@Configuration
public class Beans {

    /**
     * 配置jackson的ObjectMapper
     *
     * @return ObjectMapper
     */
    @Bean(name = "objectMapper")
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);


        return objectMapper;
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
