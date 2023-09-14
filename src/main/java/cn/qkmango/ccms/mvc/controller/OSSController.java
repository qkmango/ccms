package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.mvc.service.OSSService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-09-12 22:16
 */
@RestController
@RequestMapping("oss")
public class OSSController {

    @Resource
    private OSSService service;

    @Resource
    private ReloadableResourceBundleMessageSource ms;


    @PostMapping("upload/avatar.do")
    public R uploadAvatar(@NotNull MultipartFile avatar) {
        return service.upload(avatar, 2222156148);
    }

    @GetMapping("one/avatar.do")
    public R getAvatarUrl() {
        Locale locale = LocaleContextHolder.getLocale();
        String url = service.getAvatarUrl(2222156148);
        return url == null ?
                R.fail(ms.getMessage("response.file.get.failure", null, locale)) :
                R.success(url);
    }

}
