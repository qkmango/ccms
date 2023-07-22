package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.map.R;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

    private static final Map<String, String> zh_CN = new HashMap<>(1);
    private static final Map<String, String> en_US = new HashMap<>(1);

    static {
        zh_CN.put("locale", "zh_CN");
        en_US.put("locale", "en_US");
    }

    /**
     * 获取当前语言环境
     * <p>
     * 基于cookie的语言环境切换
     *
     * @param locale 当前语言环境
     * @return 更改后的语言环境
     * 前端直接修改cookie即可，修改后的cookie会随请求发送到后端，实现国际化
     * 前端设置cookie的方法：
     * <li>locale: zh_CN <pre>&lt;script>document.cookie='locale=zh_CN'&lt;/script></pre></li>
     * <li>locale: en_US <pre>&lt;script>document.cookie='locale=en_US'&lt;/script></pre></li>
     */
    @RequestMapping("locale.do")
    public R<Locale> locale() {
        System.out.println(LocaleContextHolder.getLocale().hashCode());
        return R.success(LocaleContextHolder.getLocale());
    }

}
