package cn.qkmango.ccms.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("static/template/mail_captcha_template.html");
        // File file = new ClassPathResource("static/template/mail_captcha_template.html").getFile();

        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();

        //读取BufferedReader中的内容并放到StringBuilder中
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        reader.close();
        br.close();

        // return FileUtils.readFileToString(file, "UTF-8");
        return sb.toString();
    }

}
