package cn.qkmango.ccms.config;

import cn.qkmango.ccms.common.util.MailTemplate;
import cn.qkmango.ccms.common.util.SnowFlake;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
     * 邮件模板
     */
    @Bean(name = "mailTemplate")
    public MailTemplate mailTemplate() {
        String format = "<div style=\"position:relative;font-size:14px;height:auto;padding:15px 15px 10px 15px;z-index:1;zoom:1;line-height:1.7\"><div><div style=\"opacity:1\"><style type=\"text/css\">p{padding:0;margin:0;color:#333;font-size:16px}svg{padding:15px 0 15px 30px;height:50px}</style><div style=\"max-width:800px;padding-bottom:10px;margin:20px auto 0 auto\"><table cellpadding=\"0\" cellspacing=\"0\" style=\"background-color:#fff;border-collapse:collapse;border:1px solid #e5e5e5;box-shadow:0 10px 15px rgba(0,0,0,.05);text-align:left;width:100%;font-size:14px;border-spacing:0\"><tbody><tr style=\"background-color:#fcfcfc;border:1px solid #E3E9ED\"><td><svg version=\"1.1\" id=\"_1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" viewBox=\"0 0 1758 341\" style=\"enable-background:new 0 0 1758 341\" xml:space=\"preserve\"><style type=\"text/css\">.st0{fill:#43A756}.st1{fill:#383C92}</style><g><g><path class=\"st0\" d=\"M486.8,119.1h52.4L498,312.9h-66.3l16-75.1h-2c-5.1,8.2-19.7,23.5-46.9,23.5c-30.3,0-41.5-19-41.5-44.9" +
                "c0-17.3,5.1-100.6,71.1-100.6c15.3,0,35,3.7,41.5,24.5h2L486.8,119.1z M422.5,215.3c0,8.5,2.4,15.3,9.5,15.3" +
                "c8.5,0,16.3-8.5,20.1-12.9l12.6-59.2c-3.1-5.1-8.2-11.2-14.6-11.2C429.6,147.3,422.5,200.7,422.5,215.3z\"/><path class=\"st0\" d=\"M623.8,177.9l75.5-58.8h42.8l-64.6,53l48.3,86.4h-72.4l-33-64.9l-13.6,64.9h-66l45.9-217.6H653L623.8,177.9z\"/><path class=\"st0\" d=\"M828.1,139.8c0,0,21.8-24.1,56.1-24.1c20.7,0,30.6,10.5,32,24.5h1.7c0,0,24.8-24.5,55.1-24.5" +
                "c22.4,0,38.1,13.9,38.1,33c0,10.2-4.4,27.5-8.5,46.6l-13.6,63.2H925l17.7-84.7c1.4-6.5,2.7-12.9,2.7-16.7c0-6.1-3.4-10.9-9.9-10.9" +
                "c-8.2,0-16,7.8-19.7,11.9l-21.1,100.3h-60.5l17.7-84.7c1-5.4,2.7-11.6,2.7-16.3c0-6.5-3.4-11.2-9.9-11.2" +
                "c-7.8,0-15.6,7.5-19.4,11.6L804,258.5h-66l29.6-139.4h52.7l5.8,20.7H828.1z\"/><path class=\"st0\" d=\"M1046,131.3c21.8-10.9,43.9-15.6,72.4-15.6c55.1,0,70.7,18,70.7,46.2c0,8.2-1.4,18.7-2.7,25.2l-15.3,71.4h-52" +
                "l-5.8-20.7h-2c-13.6,15-33.7,23.5-51.3,23.5c-20.7,0-41.5-11.6-41.5-39.1c0-44.9,54.4-49,105.4-49c0.7-4.4,2.7-13.9,2.7-20.1" +
                "c0-7.5-3.1-15.6-16-15.6c-15,0-21.4,10.2-23.5,22.4h-47.3L1046,131.3z M1120.5,188.1h-5.8c-25.2,0-34.7,14.3-34.7,27.2" +
                "c0,10.5,6.1,15.6,13.6,15.6c8.8,0,19.7-7.1,21.8-17.7L1120.5,188.1z\"/><path class=\"st0\" d=\"M1290.2,139.8c0,0,22.1-24.1,55.1-24.1c22.1,0,38.8,12.2,38.8,32c0,11.6-5.4,33.3-8.5,47.6l-13.6,63.2h-65.6" +
                "l17.7-84.7c1-5.4,2.7-11.6,2.7-16.3c0-6.5-3.4-11.2-9.9-11.2c-7.8,0-15.6,7.5-19.4,11.6L1266,258.5h-66l29.6-139.4h52.7l5.8,20.7" +
                "H1290.2z\"/><path class=\"st0\" d=\"M1526.5,119.1h52.4l-26.5,126.1c-8.2,38.4-34,67.7-103.7,67.7c-12.9,0-38.8-1-49.3-1.7l6.1-23.1" +
                "c13.6,1.7,29.2,1.7,39.8,1.7c25.8,0,33.7-8.2,38.8-33.7l4.1-21.1h-2.4c-5.1,8.2-19.7,23.5-46.9,23.5c-30.3,0-41.5-19-41.5-44.9" +
                "c0-16.7,4.8-97.9,71.7-97.9c14.3,0,34.3,3.7,40.8,24.5h2L1526.5,119.1z M1462.5,212.9c0,8.2,2.4,15,9.9,15" +
                "c8.5,0,17.3-9.5,20.1-13.3l11.9-56.4c-3.1-5.1-7.8-10.9-14.3-10.9C1469.7,147.3,1462.5,199,1462.5,212.9z\"/><path class=\"st0\" d=\"M1739.6,129.3c12.2,9.5,18.4,25.5,18.4,47.3c0,26.9-9.2,49.6-25.8,63.9c-16.3,13.9-39.8,20.7-71.1,20.7" +
                "c-26.9,0-45.6-5.1-57.1-15.3c-11.9-10.5-18.4-26.9-18.4-47.6c0-27.9,11.9-52,33.3-66.6c16-10.9,36.7-16,64.6-16" +
                "C1709.4,115.7,1728.1,120.1,1739.6,129.3z M1692.4,156.1c0-11.9-1.7-18.4-9.5-18.4c-23.5,0-32.6,57.5-32.6,80.2" +
                "c0,13.6,3.4,21.1,11.6,21.1C1683.5,239.1,1692.4,183.3,1692.4,156.1z\"/></g></g><g><path class=\"st1\" d=\"M175.1,341h-32c-14.9,0-27.1-12.1-27.1-27.1V27.3c0-14.9,12.1-27.1,27.1-27.1h32c14.9,0,27.1,12.1,27.1,27.1" +
                "v286.6C202.2,328.9,190,341,175.1,341z\"/><path class=\"st1\" d=\"M299.1,71.5l16,27.7c7.5,12.9,3,29.5-9.9,37L56.9,279.4c-12.9,7.5-29.5,3-37-9.9L4,241.8" +
                "c-7.5-12.9-3-29.5,9.9-37L262.1,61.6C275,54.1,291.6,58.5,299.1,71.5z\"/><path class=\"st1\" d=\"M315,241.8l-16,27.7c-7.5,12.9-24,17.4-37,9.9L13.9,136.1c-12.9-7.5-17.4-24-9.9-37l16-27.7" +
                "c7.5-12.9,24-17.4,37-9.9l248.2,143.3C318.1,212.3,322.5,228.9,315,241.8z\"/></g></svg></td></tr><tr><td style=\"padding:30px\"><h1 style=\"font-size:26px;font-weight:700;color:#00965e\">您的验证码</h1><p style=\"line-height:2\">验证码有效期为5分钟, 不要把验证码告诉别人哦~</p></td></tr><tr><td style=\"padding:0 30px 0 30px\"><p style=\"color:#00965e;text-align:center;line-height:1.75em;background-color:#f2f2f2;min-width:200px;margin:0 auto;font-size:28px;border-radius:5px;border:1px solid #d9d9d9;font-weight:700\">$</p></td></tr><tr><td style=\"padding:30px\"><p style=\"line-height:2\">这一封邮件包括一些您的账号信息，请不要回复或转发它，以免带来不必要的信息泄露风险。</p></td></tr><tr><td style=\"padding:30px\"><hr style=\"background-color:#d9d9d9;border:none;height:1px\"><p style=\"text-align:center;line-height:2\">CCMS 校园一卡通系统</p></td></tr></tbody></table></div></div></div></div>";
        return new MailTemplate(format);
    }

    /**
     * 雪花算法
     */
    @Bean(name = "snowFlake")
    public SnowFlake snowFlake() {
        return new SnowFlake();
    }

}

