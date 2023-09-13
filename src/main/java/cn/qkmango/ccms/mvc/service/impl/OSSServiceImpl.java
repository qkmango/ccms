package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.middleware.oss.AvatarOSSClient;
import cn.qkmango.ccms.mvc.service.OSSService;
import jakarta.annotation.Resource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-09-12 23:11
 */
@Service
public class OSSServiceImpl implements OSSService {
    @Resource
    private AvatarOSSClient avatarOSSClient;

    @Resource
    private ReloadableResourceBundleMessageSource ms;

    // 上传的文件最大字节 1048576 byte = 131072 Byte = 128KB
    private static final long FILE_MAX_SIZE = 128 * 1024;


    @Override
    public R upload(MultipartFile file, String account) {
        Locale locale = LocaleContextHolder.getLocale();

        // 判断文件类型是否为 jpg
        if (!"image/jpeg".equals(file.getContentType())) {
            R.fail(ms.getMessage("response.file-type-not-support", null, locale));
        }

        // 文件过大
        if (file.getSize() > FILE_MAX_SIZE) {
            return R.fail(ms.getMessage("response.file-max-size", new String[]{FILE_MAX_SIZE + "KB"}, locale));
        }

        // 存储到 OSS
        String path = avatarOSSClient.upload(file, account + ".jpg");
        return path == null ?
                R.fail(ms.getMessage("response.file.upload.failure", null, locale)) :
                R.success(path, ms.getMessage("response.file.upload.success", null, locale));
    }


    @Override
    public String getAvatarUrl(String account) {
        return avatarOSSClient.get(account + ".jpg");
    }
}
