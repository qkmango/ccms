package cn.qkmango.ccms.config;

import cn.qkmango.ccms.common.util.SnowFlake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置雪花算法
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-25 14:44
 */
@Configuration
public class UtilsConfig {
    @Bean(name = "snowFlake")
    public SnowFlake snowFlake() {
        return new SnowFlake(1, 1);
    }
}
