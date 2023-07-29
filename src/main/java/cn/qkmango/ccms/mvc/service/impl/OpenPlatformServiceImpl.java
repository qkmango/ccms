package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.cache.security.SecurityCache;
import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.bo.OpenPlatformBo;
import cn.qkmango.ccms.domain.entity.OpenPlatform;
import cn.qkmango.ccms.mvc.dao.OpenPlatformDao;
import cn.qkmango.ccms.mvc.service.OpenPlatformService;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

    @Resource(name = "authAccessCodeCache")
    private SecurityCache authAccessCodeCache;

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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void bind(Integer account, String accessCode) {

        //  
        /**
         * 从缓存中获取 accessCode 对应的平台信息
         * <p>此处的 accessCode 授权码为第三方认证后回调 {@link cn.qkmango.ccms.mvc.controller.AuthenticationController#callback(PlatformType, String, Map)}
         *   时返回的重定向URL中的授权码, 在{@link cn.qkmango.ccms.mvc.service.impl.AuthenticationServiceImpl#callback(String, PlatformType, Map)} 处生成并缓存
         * </p>
         */
        String value = authAccessCodeCache.get(accessCode);
        authAccessCodeCache.delete(accessCode);
        if (value == null) {
            throw new RuntimeException(ms.getMessage("db.update.open-platform.bind.failure", null, LocaleContextHolder.getLocale()));
        }
        OpenPlatformBo platformBo = JSON.parseObject(value, OpenPlatformBo.class);

        //先判断是否为绑定过的平台
        //如果是绑定过的平台，说明是登陆使用的 accessCode，不能用作绑定
        if (platformBo.getBind()) {
            throw new RuntimeException(ms.getMessage("db.update.open-platform.bind.failure", null, LocaleContextHolder.getLocale()));
        }

        OpenPlatform platform = new OpenPlatform()
                .setType(platformBo.getType())
                .setAccount(account)
                .setUid(platformBo.getUid());

        // 新增绑定记录
        int affectedRows = dao.insert(platform);
        if (affectedRows != 1) {
            throw new RuntimeException(ms.getMessage("db.update.open-platform.bind.failure", null, LocaleContextHolder.getLocale()));
        }
    }
}
