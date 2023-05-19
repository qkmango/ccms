package cn.qkmango.ccms.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-19 11:43
 */
@ConfigurationProperties(prefix = "ccms.authentication")
@PropertySource(value = "classpath:application.yml", encoding = "utf-8")
public class GiteeAppConfig extends AppConfig{

}
