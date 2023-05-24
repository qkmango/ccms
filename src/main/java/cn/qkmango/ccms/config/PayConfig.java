package cn.qkmango.ccms.config;


import cn.qkmango.ccms.pay.AlipayConfig;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-05-24 14:12
 */
@Configuration
public class PayConfig {

    @Bean("alipayConfig")
    @ConfigurationProperties(prefix = "ccms.pay.alipay")
    public AlipayConfig alipayConfig() {
        return new AlipayConfig();
    }

    @Bean("alipayClient")
    public AlipayClient alipayClient() {
        AlipayConfig alipayConfig = alipayConfig();
        return new DefaultAlipayClient(
                alipayConfig.gateway,
                alipayConfig.id,
                alipayConfig.appPrivateKey,
                "JSON",
                "UTF-8",
                alipayConfig.alipayPublicKey,
                "RSA2");

    }

}
