package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.entity.OpenPlatform;
import cn.qkmango.ccms.mvc.dao.OpenPlatformDao;
import cn.qkmango.ccms.mvc.service.OpenPlatformService;
import jakarta.annotation.Resource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-25 18:03
 */
@Service
public class OpenPlatformServiceImpl implements OpenPlatformService {
    @Resource
    private ReloadableResourceBundleMessageSource ms;

    @Resource
    private OpenPlatformDao dao;

    @Override
    public List<OpenPlatform> state(Integer id) {
        return dao.state(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void unbind(Integer account, PlatformType type) {
        int affectedRows = dao.unbind(account, type);
        if (affectedRows != 1) {
            throw new RuntimeException(ms.getMessage("db.update.open-platform.unbind.failure", null, LocaleContextHolder.getLocale()));
        }
    }
}
