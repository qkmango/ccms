package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.exception.permission.LoginException;
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
    private ReloadableResourceBundleMessageSource messageSource;

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

    @Override
    public String callback(String state, PlatformType platform, Map<String, String> params) {
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
            String message = messageSource.getMessage("response.authentication.state.failure", null, LocaleContextHolder.getLocale());
            return builder
                    .with("success", false)
                    .with("message", message)
                    .build().url();
        }



        return null;
    }

    /**
     * Gitee 回调
     * 回调中进行从Gitee获取用户信息，然后和系统数据库进行比对登陆
     *
     * @param state  用于第三方应用防止CSRF攻击, 有效期为5分钟
     * @param params 第三方回调参数,
     *               code               授权码
     *               error              错误码
     *               error_description  错误描述
     * @return 返回重定向页面
     */
    @Override
    public String giteeCallback(String state, Map<String, String> params) {
        String code = params.get("code");
        String error = params.get("error");

        //获取重定向地址
        RequestURL.Builder builder = giteeAuthHttpClient.getAppConfig().getRedirect().builder()
                .with("platform", "gitee");

        //检查state是否存在
        boolean checked = authStateCache.check(state);
        if (!checked) {
            String message = messageSource.getMessage("response.authentication.state.failure", null, LocaleContextHolder.getLocale());
            return builder
                    .with("success", false)
                    .with("message", message)
                    .build().url();
        }

        //获取第三方认证结果
        AuthenticationResult result = giteeAuthHttpClient.authentication(state, code, error);

        //第三方认证成功
        if (result.success) {
            //检查账户
            Account account = this.checkAccount(result.getUserInfo().getUid());
            if (account == null) {
                return null;
            }
            //生成授权码
            String authorizationCode = authCodeCache.create(account.getId().toString());
            return authorizationCode;
        }
        return null;
    }


    /**
     * dingtalk 回调
     * 回调中进行从钉钉获取用户信息，然后和系统数据库进行比对登陆
     *
     * @param state  用于第三方应用防止CSRF攻击, 有效期为5分钟
     * @param params 第三方回调参数,
     *               authCode 授权码
     * @return 返回重定向页面
     */
    @Override
    public String dingtalkCallback(String state, Map<String, String> params) {

        String code = params.get("authCode");

        //获取重定向地址
        RequestURL.Builder builder = dingtalkAuthHttpClient.getAppConfig().getRedirect().builder()
                .with("platform", "dingtalk");


        //检查state是否存在
        boolean checked = authStateCache.check(state);
        if (!checked) {
            String message = messageSource.getMessage("response.authentication.state.failure", null, LocaleContextHolder.getLocale());
            return builder
                    .with("success", false)
                    .with("message", URLEncoder.encode(message, StandardCharsets.UTF_8))
                    .build().url();
        }

        //获取第三方认证结果
        AuthenticationResult result = dingtalkAuthHttpClient.authentication(state, code);

        //第三方认证成功
        if (result.success) {
            //检查账户
            Account account = this.checkAccount(result.getUserInfo().getUid());
            if (account == null) {
                return null;
            }
            //生成授权码
            String authorizationCode = authCodeCache.create(account.getId().toString());
            return authorizationCode;
        }
        return null;
    }


    /**
     * alipay 回调
     *
     * @param state  用于第三方应用防止CSRF攻击, 有效期为5分钟
     * @param params 第三方回调参数,
     *               auth_code 授权码
     *               state     状态
     *               app_id    应用id
     *               source    来源
     *               scope     作用域
     * @return 返回重定向页面
     */
    @Override
    public String alipayCallback(String state, Map<String, String> params) {

        String authCode = params.get("auth_code");
        String appId = params.get("app_id");
        String source = params.get("source");
        String scope = params.get("scope");

        //获取重定向地址
        RequestURL.Builder builder = alipayAuthHttpClient.getAppConfig().getRedirect().builder()
                .with("platform", "alipay");

        //检查state是否存在
        boolean checked = authStateCache.check(state);
        if (!checked) {
            String message = messageSource.getMessage("response.authentication.state.failure", null, LocaleContextHolder.getLocale());
            return builder
                    .with("success", false)
                    .with("message", URLEncoder.encode(message, StandardCharsets.UTF_8))
                    .build().url();
        }

        //获取第三方认证结果
        AuthenticationResult result = alipayAuthHttpClient.authentication(state, authCode);

        //第三方认证成功
        if (result.success) {
            //检查账户
            Account account = this.checkAccount(result.getUserInfo().getUid());
            if (account == null) {
                return null;
            }
            //生成授权码
            String authorizationCode = authCodeCache.create(account.getId().toString());
            return authorizationCode;
        }
        return null;
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
    public void toBind(OpenPlatform platform,
                       Account account) throws UpdateException {

        //如果已经绑定，抛出异常
        if (isBind(platform, account)) {
            throw new UpdateException(messageSource.getMessage("db.update.authentication.bind.failure@exist", null, LocaleContextHolder.getLocale()));
        }

        //绑定
        int affectedRows = dao.toBind(platform, account);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.update.authentication.bind.failure", null, LocaleContextHolder.getLocale()));
        }
    }

    /**
     * 判断账户指定平台是否绑定
     *
     * @param platform 平台
     * @param account  账户
     * @return 返回绑定状态
     */
    public boolean isBind(OpenPlatform platform,
                          Account account) {
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
            throw new UpdateException(messageSource.getMessage("db.update.authentication.unbind.failure", null, LocaleContextHolder.getLocale()));
        }
        return R.success(messageSource.getMessage("db.update.authentication.unbind.success", null, LocaleContextHolder.getLocale()));
    }

    /**
     * 检查账户
     * 检查账户是否存在，是否正常
     *
     * @param uid
     * @return
     */
    public Account checkAccount(String uid) {
        OpenPlatform openPlatform = openPlatformDao.getRecordByUid(uid);
        if (openPlatform == null) {
            throw new LoginException(messageSource.getMessage("db.platform.failure@unbind", null, LocaleContextHolder.getLocale()));
        }

        Account account = accountDao.getRecordById(openPlatform.getAccount());
        if (account == null) {
            throw new LoginException(messageSource.getMessage("db.account.failure@notExist", null, LocaleContextHolder.getLocale()));
        }

        if (account.getState() != AccountState.normal) {
            throw new LoginException(messageSource.getMessage("db.account.failure@state", null, LocaleContextHolder.getLocale()));
        }
        return account;
    }


}
