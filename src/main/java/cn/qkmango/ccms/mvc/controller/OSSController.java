package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.mvc.service.OSSService;
import cn.qkmango.ccms.security.holder.AccountHolder;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
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
@Validated
@RestController
@RequestMapping("oss")
public class OSSController {

    @Resource
    private OSSService service;

    @Resource
    private ReloadableResourceBundleMessageSource ms;


    /**
     * 上传头像
     */
    @PostMapping("upload/avatar.do")
    public R uploadAvatar(@NotNull MultipartFile avatar) {
        Integer id = AccountHolder.getId();
        // TODO 账号最大值超过int
        return service.upload(avatar, id);
    }

    /**
     * 获取头像URL
     */
    @GetMapping("get/avatar.do")
    public R getAvatarUrl() {
        Locale locale = LocaleContextHolder.getLocale();
        // TODO 账号最大值超过int
        Integer id = AccountHolder.getId();
        String url = service.getAvatarUrl(id);
        return url == null ?
                R.fail(ms.getMessage("response.file.get.failure", null, locale)) :
                R.success(url);
    }

    /**
     * 判断头像是否存在
     */
    @GetMapping("exist/avatar.do")
    public R existAvatar() {
        Integer id = AccountHolder.getId();
        boolean exist = service.existAvatar(id);
        return R.success(exist);
    }

}
