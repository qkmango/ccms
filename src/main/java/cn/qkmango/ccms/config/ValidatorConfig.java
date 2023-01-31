package cn.qkmango.ccms.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * 校验器配置
 * 主要配置校验器的国际化
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-31 21:10
 */
@Configuration
public class ValidatorConfig {

    @Resource
    private ReloadableResourceBundleMessageSource message;

    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(message);
        return localValidatorFactoryBean;
    }


}
