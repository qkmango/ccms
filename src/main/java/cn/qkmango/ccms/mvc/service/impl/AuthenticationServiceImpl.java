package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.bind.AccountState;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.OpenPlatform;
import cn.qkmango.ccms.mvc.dao.AccountDao;
import cn.qkmango.ccms.mvc.dao.AuthenticationDao;
import cn.qkmango.ccms.mvc.dao.OpenPlatformDao;
import cn.qkmango.ccms.mvc.service.AuthenticationService;
import cn.qkmango.ccms.security.AuthenticationResult;
import cn.qkmango.ccms.security.cache.SecurityCache;
import cn.qkmango.ccms.security.client.AuthHttpClient;
import cn.qkmango.ccms.security.holder.AccountHolder;
import cn.qkmango.ccms.security.request.RequestURL;
import jakarta.annotation.Resource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 第三方授权登陆
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-18 19:22
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Resource
    private AuthenticationDao dao;

    @Resource
    private OpenPlatformDao openPlatformDao;

    @Resource
    private AccountDao accountDao;

    @Resource(name = "authStateCache")
    private SecurityCache authStateCache;

    @Resource(name = "authCodeCache")
    private SecurityCache authCodeCache;

    @Resource
    private ReloadableResourceBundleMessageSource ms;

    @Resource(name = "giteeAuthHttpClient")
    private AuthHttpClient giteeAuthHttpClient;

    @Resource(name = "dingtalkAuthHttpClient")
    private AuthHttpClient dingtalkAuthHttpClient;

    @Resource(name = "alipayAuthHttpClient")
    private AuthHttpClient alipayAuthHttpClient;

    /**
     * 获取授权登陆地址
     *
     * @param authAccount 授权信息
     * @return 返回授权地址
     */
    @Override
    public String authorize(AuthenticationAccount authAccount) {

        // 用于第三方应用防止CSRF攻击
        String state = authStateCache.create();
        //获取 第三方平台
        PlatformType platform = authAccount.getPlatform();

        //授权地址
        String url = null;

        switch (platform) {
            case gitee -> url = giteeAuthHttpClient.authorize(authAccount, state);
            case dingtalk -> url = dingtalkAuthHttpClient.authorize(authAccount, state);
            case alipay -> url = alipayAuthHttpClient.authorize(authAccount, state);
        }
        return url;
    }

    /**
     * 第三方授权登陆回调
     *
     * @param state    防止CSRF攻击
     * @param platform 第三方平台
     * @param params   回调参数
     *                 <p>Gitee 的回调参数</p>
     *                 <ul>
     *                 <li>code               授权码</li>
     *                 <li>error              错误码</li>
     *                 <li>error_description  错误描述</li>
     *                 </ul>
     *
     *                 <p>dingtalk 的回调参数</p>
     *                 <ul><li>authCode 授权码</li></ul>
     *
     *                 <p>alipay 的回调参数</p>
     *                 <ul>
     *                 <li>auth_code 授权码</li>
     *                 <li>state     状态</li>
     *                 <li>app_id    应用id</li>
     *                 <li>source    来源</li>
     *                 <li>scope     作用域</li>
     *                 </ul>
     * @return 重定向地址
     */
    @Override
    public String callback(String state, PlatformType platform, Map<String, String> params) {
        String message = null;

        //获取重定向地址
        RequestURL.Builder builder = null;
        switch (platform) {
            case gitee -> builder = giteeAuthHttpClient.getAppConfig().getRedirect().builder()
                    .with("platform", "gitee");
            case dingtalk -> builder = dingtalkAuthHttpClient.getAppConfig().getRedirect().builder()
                    .with("platform", "dingtalk");
            case alipay -> builder = alipayAuthHttpClient.getAppConfig().getRedirect().builder()
                    .with("platform", "alipay");
        }

        boolean check = authStateCache.check(state);
        if (!check) {
            message = ms.getMessage("response.authentication.state.failure", null, LocaleContextHolder.getLocale());
            return builder
                    .with("success", false)
                    .with("message", URLEncoder.encode(message, StandardCharsets.UTF_8))
                    .build().url();
        }

        //获取第三方认证结果
        AuthenticationResult result = null;
        switch (platform) {
            case gitee -> result = giteeAuthHttpClient.authentication(state, params.get("code"), params.get("error"));
            case alipay -> result = alipayAuthHttpClient.authentication(state, params.get("auth_code"));
            case dingtalk -> result = dingtalkAuthHttpClient.authentication(state, params.get("authCode"));
        }

        //第三方认证成功
        //检查账户
        if (result.success) {
            //是否绑定
            OpenPlatform platformRecord = openPlatformDao.getRecordByUid(result.getUserInfo().getUid());
            if (platformRecord == null) {
                message = ms.getMessage("db.platform.failure@unbind", null, LocaleContextHolder.getLocale());
                return builder
                        .with("success", false)
                        .with("message", URLEncoder.encode(message, StandardCharsets.UTF_8))
                        .build().url();
            }

            // 检查账户是否存在
            Account accountRecord = accountDao.getRecordById(platformRecord.getAccount());
            if (accountRecord == null) {
                message = ms.getMessage("db.account.failure@notExist", null, LocaleContextHolder.getLocale());
                return builder
                        .with("success", false)
                        .with("message", URLEncoder.encode(message, StandardCharsets.UTF_8))
                        .build().url();
            }

            // 检查账户状态
            if (accountRecord.getState() != AccountState.normal) {
                message = ms.getMessage("db.account.failure@state", null, LocaleContextHolder.getLocale());
                return builder
                        .with("success", false)
                        .with("message", URLEncoder.encode(message, StandardCharsets.UTF_8))
                        .build().url();
            }

            // 生成授权码，并将其缓存到redis中，有效期为5分钟，key为授权码，value为账户id
            String authorizationCode = authCodeCache.create(accountRecord.getId().toString());

            message = ms.getMessage("response.authentication.success", null, LocaleContextHolder.getLocale());
            return builder
                    .with("success", true)
                    .with("message", URLEncoder.encode(message, StandardCharsets.UTF_8))
                    .with("authorizationCode", authorizationCode)
                    .build().url();
        }
        message = ms.getMessage("response.authentication.failure", null, LocaleContextHolder.getLocale());
        return builder
                .with("success", false)
                .with("message", URLEncoder.encode(message, StandardCharsets.UTF_8))
                .build().url();
    }

    /**
     * 获取开放平台绑定状态
     *
     * @return 返回开放平台绑定状态
     */
    @Override
    public List<OpenPlatform> openPlatformState() {
        Account account = AccountHolder.getAccount();
        return dao.openPlatformBindState(account);
    }

    /**
     * 绑定第三方账户
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void toBind(OpenPlatform platform, Account account) throws UpdateException {

        //如果已经绑定，抛出异常
        if (isBind(platform, account)) {
            throw new UpdateException(ms.getMessage("db.update.authentication.bind.failure@exist", null, LocaleContextHolder.getLocale()));
        }

        //绑定
        int affectedRows = dao.toBind(platform, account);
        if (affectedRows != 1) {
            throw new UpdateException(ms.getMessage("db.update.authentication.bind.failure", null, LocaleContextHolder.getLocale()));
        }
    }

    /**
     * 判断账户指定平台是否绑定
     *
     * @param platform 平台
     * @param account  账户
     * @return 返回绑定状态
     */
    public boolean isBind(OpenPlatform platform, Account account) {
        return dao.isBind(platform, account);
    }

    /**
     * 解绑开放平台
     *
     * @param platform 平台类型
     * @return 返回解绑结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public R unbind(PlatformType platform) throws UpdateException {
        Account account = AccountHolder.getAccount();
        int affectedRows = dao.unbind(platform, account);
        if (affectedRows != 1) {
            throw new UpdateException(ms.getMessage("db.update.authentication.unbind.failure", null, LocaleContextHolder.getLocale()));
        }
        return R.success(ms.getMessage("db.update.authentication.unbind.success", null, LocaleContextHolder.getLocale()));
    }

}
