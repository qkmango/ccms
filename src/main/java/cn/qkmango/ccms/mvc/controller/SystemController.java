package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.map.R;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.Locale;

/**
 * 系统
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 18:33
 */
@RestController
@RequestMapping("system")
public class SystemController {

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    /**
     * 更改语言环境
     *
     * @param locale 新的语言
     * @return 更改后的语言环境
     */
    @RequestMapping(value = "setLocale.do")
    public R<HashMap<String, String>> setLocale(String locale, Locale localeObj) {
        HashMap<String, String> custom = new HashMap<>(1);
        custom.put("locale", localeObj.getLanguage());
        return R.success(custom, messageSource.getMessage("response.setLocale.success", null, localeObj));
    }

}
