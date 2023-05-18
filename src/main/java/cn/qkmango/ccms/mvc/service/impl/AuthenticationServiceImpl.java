package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.authentication.DingtalkHttpClient;
import cn.qkmango.ccms.common.authentication.GiteeHttpClient;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.util.RedisUtil;
import cn.qkmango.ccms.common.util.UserSession;
import cn.qkmango.ccms.domain.auth.AuthenticationAccount;
import cn.qkmango.ccms.domain.auth.PlatformType;
import cn.qkmango.ccms.domain.auth.PurposeType;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.OpenPlatform;
import cn.qkmango.ccms.domain.vo.OpenPlatformBindState;
import cn.qkmango.ccms.mvc.dao.AuthenticationDao;
import cn.qkmango.ccms.mvc.service.AuthenticationService;
import cn.qkmango.ccms.security.UserInfo;
import cn.qkmango.ccms.security.request.HttpRequest;
import com.alibaba.fastjson2.JSONObject;
import com.aliyun.dingtalkcontact_1_0.models.GetUserResponseBody;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
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

    // Gitee授权登陆
    @Value("${ccms.authentication.gitee.clientId}")
    private String GITEE_CLIENT_ID;
    @Value("${ccms.authentication.gitee.callback.login}")
    private String GITEE_CALLBACK_LOGIN;

    @Value("${ccms.authentication.gitee.callback.bind}")
    private String GITEE_CALLBACK_BIND;

    // 登陆回调
    @Value("${ccms.authentication.dingtalk.callback.login}")
    private String DINGTALK_CALLBACK_LOGIN;

    // 绑定回调
    @Value("${ccms.authentication.dingtalk.callback.bind}")
    private String DINGTALK_CALLBACK_BIND;

    @Value("${ccms.authentication.dingtalk.appKey}")
    private String DINGTALK_APP_KEY;

    @Resource
    private AuthenticationDao dao;

    @Resource(name = "redisUtil")
    private RedisUtil redis;

    @Resource
    private GiteeHttpClient giteeHttpClient;

    @Resource
    private DingtalkHttpClient dingtalkHttpClient;

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Lazy
    @Autowired
    private AuthenticationServiceImpl thisService;

    @Resource(name = "giteeHttpRequest")
    private HttpRequest giteeHttpRequest;

    @Resource(name = "dingtalkHttpRequest")
    private HttpRequest dingtalkHttpRequest;

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

        //将 uuid 存入redis，用于第三方应用防止CSRF攻击，有效期为5分钟
        redis.set(state, value, 60 * 5);

        //获取 第三方平台 和 授权用途
        PlatformType platform = authAccount.getPlatform();
        PurposeType purpose = authAccount.getPurpose();

        //授权地址
        String url = null;
        //
        // //Gitee
        // if (platform == PlatformType.gitee) {
        //     url = "https://gitee.com/oauth/authorize?response_type=code&scope=user_info" +
        //             "&client_id=" + GITEE_CLIENT_ID + "&state=" + state + "&redirect_uri=";
        //     switch (purpose) {
        //         case login -> url += URLEncoder.encode(GITEE_CALLBACK_LOGIN);
        //         case bind -> url += URLEncoder.encode(GITEE_CALLBACK_BIND);
        //     }
        // }

        switch (platform) {
            case gitee -> {
                url = giteeHttpRequest.getHttpRequestBaseUrl().getAuthorizeURL().builder()
                        .with("response_type", "code")
                        .with("scope", "user_info")
                        .with("client_id", giteeHttpRequest.getClient().getId())
                        .with("state", state)
                        .with("redirect_uri", giteeHttpRequest.getHttpRequestBaseUrl().getCallbackURL(purpose).url())
                        .build();
            }
            case dingtalk -> {
                url = dingtalkHttpRequest.getHttpRequestBaseUrl().getAuthorizeURL().builder()
                        .with("response_type", "code")
                        .with("prompt", "consent")
                        .with("scope", "openid")
                        .with("client_id", dingtalkHttpRequest.getClient().getId())
                        .with("state", state)
                        .with("redirect_uri", dingtalkHttpRequest.getHttpRequestBaseUrl().getCallbackURL(purpose).url())
                        .build();
            }
            default -> {
            }
        }


        //
        // // 钉钉
        // else if (platform == PlatformType.dingtalk) {
        //     url = "https://login.dingtalk.com/oauth2/auth?response_type=code&prompt=consent&scope=openid" +
        //             "&client_id=" + DINGTALK_APP_KEY + "&state=" + state + "&redirect_uri=";
        //     switch (purpose) {
        //         case login -> url += URLEncoder.encode(DINGTALK_CALLBACK_LOGIN);
        //         case bind -> url += URLEncoder.encode(DINGTALK_CALLBACK_BIND);
        //     }
        // }
        //
        return url;

    }


    /**
     * Gitee授权回调登陆
     * 回调中进行从Gitee获取用户信息，然后和系统数据库进行比对登陆
     *
     * @param state             授权状态,防止CSRF攻击,授权状态,防止CSRF攻击,
     *                          在redis中有效期为5分钟, 拼接为 authentication:PermissionType:UUID
     * @param code              授权码
     * @param error             有错误时返回
     * @param error_description 错误描述
     * @param request           请求
     * @param locale            语言环境
     * @return 返回重定向页面
     */
    @Override
    public String giteeLogin(String state, String code, String error, String error_description, HttpServletRequest request, Locale locale) {

        //设置默认值,默认为登录失败
        String platform = "gitee";
        String purpose = "login";
        String message = messageSource.getMessage("response.authentication.failure", null, locale);
        String redirect = "redirect:/page/common/authentication/result.html?success=%s&platform=%s&purpose=%s&message=%s";

        //如果用户拒绝授权
        if (error != null || code == null) {
            return String.format(redirect, false, platform, purpose, URLEncoder.encode(message));
        }

        //判断state是否有效，防止CSRF攻击
        String value = redis.get(state);
        redis.delete(state);
        if (value == null) {
            message = messageSource.getMessage("response.authentication.state.failure", null, locale);
            return String.format(redirect, false, platform, purpose, URLEncoder.encode(message));
        }

        //获取redis中存储的授权信息,获取权限类型
        AuthenticationAccount authAccount = JSONObject.parseObject(value, AuthenticationAccount.class);

        //根据 code 获取用户信息
        PurposeType authPurpose = authAccount.getPurpose();
        JSONObject userInfo = giteeHttpClient.getUserInfoByCode(code, authPurpose);


        //查询数据库中是否存在该用户
        //如果code无效，则Gitee返回的结果中没有 id
        if (userInfo.get("id") == null) {
            return String.format(redirect, false, platform, purpose, URLEncoder.encode(message));
        }
        String uid = ((Integer) userInfo.get("id")).toString();
        authAccount.setUid(uid);

        //判断认证用途，执行 认证 的操作
        boolean login = login(authAccount, locale);
        //如果认证陆成功
        if (login) {
            message = messageSource.getMessage("response.authentication.success", null, locale);
            return String.format(redirect, true, platform, purpose, URLEncoder.encode(message));
        }
        //如果认证失败
        else {
            message = messageSource.getMessage("db.user.gitee.uid.failure@notExist", null, locale);
            return String.format(redirect, false, platform, purpose, URLEncoder.encode(message));
        }

    }


    @Override
    public String giteeBind(String state, String code, String error, String errorDescription, HttpServletRequest request, Locale locale) throws UpdateException {

        //设置默认值,默认为登录失败
        String platform = "gitee";
        String purpose = "bind";
        String message = messageSource.getMessage("response.authentication.failure", null, locale);
        String redirect = "redirect:/page/common/authentication/result.html?success=%s&platform=%s&purpose=%s&message=%s";

        //如果用户拒绝授权
        if (error != null || code == null) {
            return String.format(redirect, false, platform, purpose, URLEncoder.encode(message));
        }

        //判断state是否有效，防止CSRF攻击
        String value = redis.get(state);
        redis.delete(state);
        if (value == null) {
            message = messageSource.getMessage("response.authentication.state.failure", null, locale);
            return String.format(redirect, false, platform, purpose, URLEncoder.encode(message));
        }

        //获取redis中存储的授权信息,获取权限类型
        AuthenticationAccount authenticationAccount = JSONObject.parseObject(value, AuthenticationAccount.class);

        //根据 code 获取用户信息
        PurposeType authPurpose = authenticationAccount.getPurpose();
        JSONObject userInfo = giteeHttpClient.getUserInfoByCode(code, authPurpose);
        if (userInfo.get("id") == null) {
            return String.format(redirect, false, platform, purpose, URLEncoder.encode(message));
        }
        String uid = ((Integer) userInfo.get("id")).toString();
        authenticationAccount.setUid(uid);

        //判断认证用途，执行 绑定 的操作
        Account account = UserSession.getAccount();
        OpenPlatform openPlatform = new OpenPlatform(account.getId(), PlatformType.gitee, true, uid);
        thisService.toBind(openPlatform, account, locale);

        message = messageSource.getMessage("db.update.authentication.bind.success", null, locale);
        return String.format(redirect, true, platform, purpose, URLEncoder.encode(message));
    }


    /**
     * 钉钉回调登陆
     * 回调中进行从钉钉获取用户信息，然后和系统数据库进行比对登陆
     *
     * @param code  授权码
     * @param state 授权状态,防止CSRF攻击,授权状态,防止CSRF攻击
     * @return 返回重定向页面
     */
    @Override
    public String dingtalkLogin(String code, String state, Locale locale) {

        //设置默认值,默认为登录失败
        String platform = "dingtalk";
        String purpose = "login";
        String message = messageSource.getMessage("response.authentication.failure", null, locale);
        String redirect = "redirect:/page/common/authentication/result.html?success=%s&platform=%s&purpose=%s&message=%s";

        //如果用户拒绝授权
        if (code == null) {
            return String.format(redirect, false, platform, purpose, URLEncoder.encode(message));
        }

        //判断state是否有效，防止CSRF攻击
        String value = redis.get(state);
        redis.delete(state);
        if (value == null) {
            message = messageSource.getMessage("response.authentication.state.failure", null, locale);
            return String.format(redirect, false, platform, purpose, URLEncoder.encode(message));
        }

        //根据 code 获取用户信息
        // GetUserResponseBody userInfo = dingtalkHttpClient.getUserInfoByCode(code);
        // GetUserResponseBody userInfo = null;
        UserInfo userInfo = dingtalkHttpRequest.getUserInfoByCode(code);

        if (userInfo == null) {
            return String.format(redirect, false, platform, purpose, URLEncoder.encode(message));
        }

        //获取redis中存储的授权信息，获取权限类型
        AuthenticationAccount authAccount = JSONObject.parseObject(value, AuthenticationAccount.class);
        authAccount.setUid(userInfo.getUid());

        System.out.println("authAccount = " + authAccount);

        //查询数据库中是否存在该用户
        // AuthenticationAccount authenticationAccount = new AuthenticationAccount();
        // authenticationAccount.setPermission(authAccount.getPermission());
        // authenticationAccount.setPlatform(authAccount.getPlatform());

        boolean login = login(authAccount, locale);
        //如果认证陆成功
        if (login) {
            message = messageSource.getMessage("response.authentication.success", null, locale);
            return String.format(redirect, true, platform, purpose, URLEncoder.encode(message));
        }
        //如果认证失败
        else {
            message = messageSource.getMessage("db.user.dingtalk.uid.failure@notExist", null, locale);
            return String.format(redirect, false, platform, purpose, URLEncoder.encode(message));
        }

    }


    @Override
    public String dingtalkBind(String code, String state, Locale locale) throws UpdateException {

        //设置默认值,默认为登录失败
        String platform = "dingtalk";
        String purpose = "bind";
        String message = messageSource.getMessage("response.authentication.failure", null, locale);
        String redirect = "redirect:/page/common/authentication/result.html?success=%s&platform=%s&purpose=%s&message=%s";

        //如果用户拒绝授权
        if (code == null) {
            return String.format(redirect, false, platform, purpose, URLEncoder.encode(message));
        }

        //判断state是否有效，防止CSRF攻击
        String value = redis.get(state);
        redis.delete(state);
        if (value == null) {
            message = messageSource.getMessage("response.authentication.state.failure", null, locale);
            return String.format(redirect, false, platform, purpose, URLEncoder.encode(message));
        }

        //获取redis中存储的授权信息,获取权限类型
        // AuthenticationAccount authenticationAccount = JSONObject.parseObject(value, AuthenticationAccount.class);

        //获取用户信息
        GetUserResponseBody userInfo = dingtalkHttpClient.getUserInfoByCode(code);
        String uid = userInfo.getUnionId();

        //判断认证用途，执行 绑定 的操作
        Account account = UserSession.getAccount();
        OpenPlatform openPlatform = new OpenPlatform(account.getId(), PlatformType.dingtalk, true, uid);
        thisService.toBind(openPlatform, account, locale);

        message = messageSource.getMessage("db.update.authentication.bind.success", null, locale);
        return String.format(redirect, true, platform, purpose, URLEncoder.encode(message));
    }


    /**
     * 获取开放平台绑定状态
     *
     * @return 返回开放平台绑定状态
     */
    @Override
    public OpenPlatformBindState openPlatformState() {
        Account account = UserSession.getAccount();
        return dao.openPlatformBindState(account);
    }


    /**
     * 授权
     *
     * @param account 认证账户
     */
    private boolean login(AuthenticationAccount account,
                          Locale locale) {

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
