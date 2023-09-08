package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.permission.LoginException;
import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.bind.AccountState;
import cn.qkmango.ccms.domain.bo.OpenPlatformBo;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.OpenPlatform;
import cn.qkmango.ccms.middleware.cache.security.SecurityCache;
import cn.qkmango.ccms.mvc.dao.AccountDao;
import cn.qkmango.ccms.mvc.dao.OpenPlatformDao;
import cn.qkmango.ccms.mvc.service.AuthenticationService;
import cn.qkmango.ccms.security.AuthenticationResult;
import cn.qkmango.ccms.security.client.AuthHttpClient;
import cn.qkmango.ccms.security.encoder.PasswordEncoder;
import cn.qkmango.ccms.security.request.RequestURL;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    private OpenPlatformDao openPlatformDao;

    @Resource
    private AccountDao accountDao;

    @Resource(name = "authStateCache")
    private SecurityCache authStateCache;

    @Resource(name = "authAccessCodeCache")
    private SecurityCache authAccessCodeCache;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private ReloadableResourceBundleMessageSource ms;

    @Resource(name = "giteeAuthHttpClient")
    private AuthHttpClient giteeAuthHttpClient;

    @Resource(name = "dingtalkAuthHttpClient")
    private AuthHttpClient dingtalkAuthHttpClient;

    @Resource(name = "alipayAuthHttpClient")
    private AuthHttpClient alipayAuthHttpClient;

    /**
     * 登陆接口
     *
     * @param account 账户
     * @return 登陆成功返回登陆用户信息
     * @throws LoginException 登陆异常
     */
    @Override
    public Account systemLogin(Account account) throws LoginException {

        Account loginAccount = accountDao.getRecordById(account.getId(), true);

        // 检查账户状态
        checkLoginAccount(loginAccount);

        // 判断密码是否正确
        String dbPassword = loginAccount.getPassword();
        boolean matches = passwordEncoder.matches(account.getPassword(), dbPassword);
        if (!matches) {
            throw new LoginException(ms.getMessage("response.login.password.error", null, LocaleContextHolder.getLocale()));
        }

        // 清除密码
        loginAccount.setPassword(null);

        // 返回登陆用户信息，创建 token 由 controller 层完成
        return loginAccount;
    }


    @Override
    public Account accessLogin(String accessCode) throws LoginException {
        // 从 redis 中获取 accessCode 对应的账户ID
        String value = authAccessCodeCache.get(accessCode);
        // 删除 accessCode
        authAccessCodeCache.delete(accessCode);

        // 判断 accessCode 是否存在/是否过期
        if (value == null) {
            throw new LoginException(ms.getMessage("response.login.access-code.not.exist", null, LocaleContextHolder.getLocale()));
        }

        // 将 value 转换为 OpenPlatformBo 对象
        OpenPlatformBo platform = JSON.parseObject(value, OpenPlatformBo.class);

        // 查询数据库
        Account loginAccount = accountDao.getRecordById(platform.getAccount(), false);
        // 检查账户状态
        checkLoginAccount(loginAccount);

        return loginAccount;
    }

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
        // 获取 第三方平台
        PlatformType platform = authAccount.getPlatform();

        // 授权地址
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

        // 获取重定向地址
        RequestURL.Builder builder = null;
        switch (platform) {
            case gitee -> builder = giteeAuthHttpClient.getAppConfig().getRedirect().builder()
                    .with("platform", "gitee");
            case dingtalk -> builder = dingtalkAuthHttpClient.getAppConfig().getRedirect().builder()
                    .with("platform", "dingtalk");
            case alipay -> builder = alipayAuthHttpClient.getAppConfig().getRedirect().builder()
                    .with("platform", "alipay");
        }

        // 检查state
        boolean check = authStateCache.checkAndDelete(state);
        if (!check) {
            message = ms.getMessage("response.authentication.state.failure", null, LocaleContextHolder.getLocale());
            return builder
                    .with("success", false)
                    .with("message", URLEncoder.encode(message, StandardCharsets.UTF_8))
                    .build().url();
        }

        // 获取第三方认证结果
        AuthenticationResult result = null;
        switch (platform) {
            case gitee -> result = giteeAuthHttpClient.authentication(state, params.get("code"), params.get("error"));
            case alipay -> result = alipayAuthHttpClient.authentication(state, params.get("auth_code"));
            case dingtalk -> result = dingtalkAuthHttpClient.authentication(state, params.get("authCode"));
        }

        // 第三方认证成功
        // 检查账户
        if (result.success) {
            // 是否绑定
            OpenPlatform platformRecord = openPlatformDao.getRecordByUid(result.getUserInfo().getUid());

            // 业务对象
            OpenPlatformBo platformBo = new OpenPlatformBo();

            // 未绑定
            // 未绑定可以进行绑定操作,所以需要生成授权码，状态为true
            // 此处生成用于绑定的 accessCode
            if (platformRecord == null) {
                platformBo
                        .setBind(false)
                        .setType(platform)
                        .setUid(result.getUserInfo().getUid());
                // 生成授权码，并将其缓存到redis中，有效期为5分钟，key为授权码，value为数据库中认证平台信息
                String accessCode = authAccessCodeCache.create(JSON.toJSONString(platformBo));
                message = ms.getMessage("db.platform.failure@unbind", null, LocaleContextHolder.getLocale());
                return builder
                        .with("success", true)
                        .with("bind", false)
                        .with("accessCode", accessCode)
                        .with("message", URLEncoder.encode(message, StandardCharsets.UTF_8))
                        .build().url();
            }

            // 已绑定
            platformBo.setBind(true);
            BeanUtils.copyProperties(platformRecord, platformBo);

            // 检查账户是否存在
            Account accountRecord = accountDao.getRecordById(platformRecord.getAccount());
            if (accountRecord == null) {
                message = ms.getMessage("db.account.failure@notExist", null, LocaleContextHolder.getLocale());
                return builder
                        .with("success", false)
                        .with("bind", true)
                        .with("message", URLEncoder.encode(message, StandardCharsets.UTF_8))
                        .build().url();
            }

            // 已绑定
            // 检查账户状态
            if (accountRecord.getState() != AccountState.normal) {
                message = ms.getMessage("db.account.failure@state", null, LocaleContextHolder.getLocale());
                return builder
                        .with("success", false)
                        .with("bind", true)
                        .with("message", URLEncoder.encode(message, StandardCharsets.UTF_8))
                        .build().url();
            }

            // 已绑定
            // 账户状态正常
            // 此处生成用于登陆的 accessCode
            // 生成授权码，并将其缓存到redis中，有效期为5分钟，key为授权码，value为数据库中认证平台信息
            String accessCode = authAccessCodeCache.create(JSON.toJSONString(platformBo));

            // √ 成功
            message = ms.getMessage("response.authentication.success", null, LocaleContextHolder.getLocale());
            return builder
                    .with("success", true)
                    .with("bind", true)
                    .with("message", URLEncoder.encode(message, StandardCharsets.UTF_8))
                    .with("accessCode", accessCode)
                    .build().url();
        }

        // 失败
        // 第三方认证失败
        message = ms.getMessage("response.authentication.failure", null, LocaleContextHolder.getLocale());
        return builder
                .with("success", false)
                .with("message", URLEncoder.encode(message, StandardCharsets.UTF_8))
                .build().url();
    }


    /**
     * 检查登录账户
     * 1. 判断账户是否存在
     * 2. 判断账户状态
     *
     * @param account
     */
    private void checkLoginAccount(Account account) {
        // 判断账户是否存在
        if (account == null) {
            throw new LoginException(ms.getMessage("db.account.failure@notExist", null, LocaleContextHolder.getLocale()));
        }

        // 判断账户状态
        switch (account.getState()) {
            case canceled ->
                    throw new LoginException(ms.getMessage("db.account.canceled.failure@canceled", null, LocaleContextHolder.getLocale()));
            case frozen ->
                    throw new LoginException(ms.getMessage("db.account.canceled.failure@frozen", null, LocaleContextHolder.getLocale()));
        }
    }

}
