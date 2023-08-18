package cn.qkmango.ccms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

/**
 * 国际化配置类
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-07-31 20:58
 */
@Configuration
public class I18nConfig {
    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(
                "classpath:i18n/db",
                "classpath:i18n/response",
                "classpath:i18n/valid"
        );

        messageSource.setUseCodeAsDefaultMessage(false);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean(name = "localeResolver")
    public CookieLocaleResolver cookieLocaleResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver("locale");
        resolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return resolver;
    }
}
