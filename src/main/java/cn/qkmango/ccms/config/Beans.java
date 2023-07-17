package cn.qkmango.ccms.config;

import cn.qkmango.ccms.common.util.SnowFlake;
import cn.qkmango.ccms.security.token.JWT;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Value;
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
        // 读取未知的枚举值为 null
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);

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
    public String mailCaptchaTemplate() throws IOException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("static/template/mail_captcha_template.html");

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

        return sb.toString();
    }

    /**
     * 雪花算法
     *
     * @return
     */
    @Bean(name = "snowFlake")
    public SnowFlake snowFlake() {
        return new SnowFlake();
    }


    @Value("${ccms.jwt.secret}")
    private String secret;
    @Value("${ccms.jwt.expire}")
    private int expire;

    @Bean(name = "jwt")
    public JWT jwt() {
        return new JWT(secret, expire);
    }

}

