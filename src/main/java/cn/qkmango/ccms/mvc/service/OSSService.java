package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.map.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-09-12 23:11
 */
public interface OSSService {
    R upload(MultipartFile file, Integer account);

    String getAvatarUrl(Integer account);

}
