package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.util.RedisUtil;
import cn.qkmango.ccms.common.util.UserSession;
import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.auth.PurposeType;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.OpenPlatform;
import cn.qkmango.ccms.mvc.dao.AuthenticationDao;
import cn.qkmango.ccms.mvc.service.AuthenticationService;
import cn.qkmango.ccms.security.AuthenticationResult;
import cn.qkmango.ccms.security.client.AuthHttpClient;
import cn.qkmango.ccms.security.request.RequestURL;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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

    @Resource(name = "redisUtil")
    private RedisUtil redis;

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Lazy
    @Autowired
    private AuthenticationServiceImpl thisService;

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
        String state = "auth:" + UUID.randomUUID();
        String value = JSONObject.toJSONString(authAccount);

        //将授权信息和state存入redis, 有效期为5分钟
        redis.set(state, value, 5 * 60);

        //获取 第三方平台
        PlatformType platform = authAccount.getPlatform();

        //授权地址
        String url = null;

        switch (platform) {
            case gitee -> url = giteeAuthHttpClient.authorize(authAccount, state, value);
            case dingtalk -> url = dingtalkAuthHttpClient.authorize(authAccount, state, value);
            case alipay -> url = alipayAuthHttpClient.authorize(authAccount, state, value);
        }
        return url;
    }


    /**
     * Gitee 回调 登陆/绑定
     * 回调中进行从Gitee获取用户信息，然后和系统数据库进行比对登陆
     *
     * @param state             授权状态,防止CSRF攻击,授权状态,防止CSRF攻击,
     *                          在redis中有效期为5分钟, 拼接为 authentication:PermissionType:UUID
     * @param code              授权码
     * @param error             有错误时返回
     * @param error_description 错误描述
     * @param locale            语言环境
     * @return 返回重定向页面
     */
    @Override
    public String giteeCallback(String state, String code, String error, String error_description, PurposeType purpose, Locale locale) throws UpdateException {

        String message = null;

        //获取重定向地址
        RequestURL.Builder builder = giteeAuthHttpClient.getAppConfig().getRedirect().builder()
                .with("platform", "gitee")
                .with("purpose", purpose.name());

        //检查state是否存在
        String value = redis.get(state);
        if (value == null) {
            message = messageSource.getMessage("response.authentication.state.failure", null, locale);
            return builder
                    .with("success", false)
                    .with("message", URLEncoder.encode(message))
                    .build().url();
        }

        //获取redis中存储的授权信息,获取权限类型
        AuthenticationAccount authAccount = JSONObject.parseObject(value, AuthenticationAccount.class);

        //获取第三方认证结果
        AuthenticationResult result = giteeAuthHttpClient.authentication(state, code, locale, error);

        //如果第三方认证失败
        if (!result.success) {
            return builder
                    .with("success", false)
                    .with("message", URLEncoder.encode(result.message))
                    .build().url();
        }

        //第三方认证成功后，登陆系统
        //登陆系统
        String uid = result.getUserInfo().getUid();
        authAccount.setUid(uid);

        switch (purpose) {
            case login -> {
                boolean login = toLogin(authAccount);
                //如果登陆成功
                if (login) {
                    message = messageSource.getMessage("response.authentication.success", null, locale);
                    return builder
                            .with("success", true)
                            .with("message", URLEncoder.encode(message))
                            .build().url();
                }
                //如果登陆失败
                else {
                    message = messageSource.getMessage("db.user.gitee.uid.failure@notExist", null, locale);
                    return builder
                            .with("success", false)
                            .with("message", URLEncoder.encode(message))
                            .build().url();
                }
            }
            case bind -> {
                Account account = UserSession.getAccount();
                OpenPlatform openPlatform = new OpenPlatform(
                        account.getId(),
                        PlatformType.gitee,
                        true,
                        uid,
                        account.getPermissionType());

                thisService.toBind(openPlatform, UserSession.getAccount(), locale);
                message = messageSource.getMessage("db.update.authentication.bind.success", null, locale);
                return builder
                        .with("success", true)
                        .with("message", URLEncoder.encode(message))
                        .build().url();
            }
        }
        return "redirect:/page/login/index.html";
    }


    /**
     * dingtalk 回调 登陆/绑定
     * 回调中进行从钉钉获取用户信息，然后和系统数据库进行比对登陆
     *
     * @param state   授权状态,防止CSRF攻击,授权状态,防止CSRF攻击
     * @param code    授权码
     * @param purpose 授权目的
     * @param locale  语言环境
     * @return 返回重定向页面
     */
    @Override
    public String dingtalkCallback(String state, String code, PurposeType purpose, Locale locale) throws UpdateException {

        String message;

        //获取重定向地址
        RequestURL.Builder builder = dingtalkAuthHttpClient.getAppConfig().getRedirect().builder()
                .with("platform", "dingtalk")
                .with("purpose", purpose.name());


        //检查state是否存在
        String value = redis.get(state);
        if (value == null) {
            message = messageSource.getMessage("response.authentication.state.failure", null, locale);
            return builder
                    .with("success", false)
                    .with("message", URLEncoder.encode(message))
                    .build().url();
        }

        //获取redis中存储的授权信息,获取权限类型
        AuthenticationAccount authAccount = JSONObject.parseObject(value, AuthenticationAccount.class);

        //获取第三方认证结果
        AuthenticationResult result = dingtalkAuthHttpClient.authentication(state, code, locale);

        //如果第三方认证失败
        if (!result.success) {
            return builder
                    .with("success", false)
                    .with("message", URLEncoder.encode(result.message))
                    .build().url();
        }

        //第三方认证成功后，登陆系统
        //登陆系统
        String uid = result.getUserInfo().getUid();
        switch (purpose) {
            case login -> {
                authAccount.setUid(uid);
                boolean login = toLogin(authAccount);
                //如果登陆成功
                if (login) {
                    message = messageSource.getMessage("response.authentication.success", null, locale);
                    return builder
                            .with("success", true)
                            .with("message", URLEncoder.encode(message))
                            .build().url();
                }
                //如果登陆成功
                else {
                    message = messageSource.getMessage("db.user.dingtalk.uid.failure@notExist", null, locale);
                    return builder
                            .with("success", false)
                            .with("message", URLEncoder.encode(message))
                            .build().url();
                }
            }
            case bind -> {
                //判断认证用途，执行 绑定 的操作
                Account account = UserSession.getAccount();
                OpenPlatform openPlatform = new OpenPlatform(
                        account.getId(),
                        PlatformType.dingtalk,
                        true,
                        uid,
                        account.getPermissionType());

                thisService.toBind(openPlatform, account, locale);

                message = messageSource.getMessage("db.update.authentication.bind.success", null, locale);
                return builder
                        .with("success", true)
                        .with("message", URLEncoder.encode(message))

                        .build().url();
            }
        }

        return "redirect:/page/login/index.html";

    }


    /**
     * alipay 回调 登陆/绑定
     *
     * @param purpose
     * @param authCode
     * @param state
     * @param appId
     * @param source
     * @param scope
     * @param locale
     * @return
     * @throws UpdateException
     */
    @Override
    public String alipayCallback(PurposeType purpose, String authCode, String state, String appId, String source, String scope, Locale locale) throws UpdateException {

        String message;

        //获取重定向地址
        RequestURL.Builder builder = alipayAuthHttpClient.getAppConfig().getRedirect().builder()
                .with("platform", "alipay")
                .with("purpose", purpose.name());

        //检查state是否存在
        String value = redis.get(state);
        if (value == null) {
            message = messageSource.getMessage("response.authentication.state.failure", null, locale);
            return builder
                    .with("success", false)
                    .with("message", URLEncoder.encode(message))
                    .build().url();
        }

        AuthenticationAccount authAccount = JSONObject.parseObject(value, AuthenticationAccount.class);

        //获取第三方认证结果
        AuthenticationResult result = alipayAuthHttpClient.authentication(state, authCode, locale);


        //如果第三方认证失败
        if (!result.success) {
            return builder
                    .with("success", false)
                    .with("message", URLEncoder.encode(result.message))
                    .build().url();
        }

        //第三方认证成功后，登陆系统
        //登陆系统
        String uid = result.getUserInfo().getUid();
        switch (purpose) {
            case login -> {
                authAccount.setUid(uid);
                boolean login = toLogin(authAccount);
                //如果登陆成功
                if (login) {
                    message = messageSource.getMessage("response.authentication.success", null, locale);
                    return builder
                            .with("success", true)
                            .with("message", URLEncoder.encode(message))
                            .build().url();
                }
                //如果登陆成功
                else {
                    message = messageSource.getMessage("db.user.alipay.uid.failure@notExist", null, locale);
                    return builder
                            .with("success", false)
                            .with("message", URLEncoder.encode(message))
                            .build().url();
                }
            }
            case bind -> {
                //判断认证用途，执行 绑定 的操作
                Account account = UserSession.getAccount();
                OpenPlatform openPlatform = new OpenPlatform(
                        account.getId(),
                        PlatformType.alipay,
                        true,
                        uid,
                        account.getPermissionType());

                thisService.toBind(openPlatform, account, locale);

                message = messageSource.getMessage("db.update.authentication.bind.success", null, locale);
                return builder
                        .with("success", true)
                        .with("message", URLEncoder.encode(message))
                        .build().url();
            }
        }

        return "redirect:/page/login/index.html";
    }

    /**
     * 获取开放平台绑定状态
     *
     * @return 返回开放平台绑定状态
     */
    @Override
    public List<OpenPlatform> openPlatformState() {
        Account account = UserSession.getAccount();
        return dao.openPlatformBindState(account);
    }


    /**
     * 授权
     *
     * @param account 认证账户
     */
    private boolean toLogin(AuthenticationAccount account) {

        Account loginAccount = dao.authentication(account);
        //如果不存在，返回错误信息
        if (loginAccount == null) {
            return false;
        }

        //登陆成功, 添加session ,返回用户信息
        loginAccount.setPermissionType(account.getPermission());
        HttpSession session = UserSession.getSession(true);
        session.setAttribute("account", loginAccount);

        return true;
    }

    /**
     * 绑定第三方账户
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void toBind(OpenPlatform platform,
                       Account account,
                       Locale locale) throws UpdateException {

        //如果已经绑定，抛出异常
        if (isBind(platform, account)) {
            throw new UpdateException(messageSource.getMessage("db.update.authentication.bind.failure@exist", null, locale));
        }

        //绑定
        int affectedRows = dao.toBind(platform, account);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.update.authentication.bind.failure", null, locale));
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
    public R unbind(PlatformType platform, Locale locale) throws UpdateException {
        Account account = UserSession.getAccount();
        int affectedRows = dao.unbind(platform, account);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.update.authentication.unbind.failure", null, locale));
        }
        return R.success(messageSource.getMessage("db.update.authentication.unbind.success", null, locale));
    }

}
