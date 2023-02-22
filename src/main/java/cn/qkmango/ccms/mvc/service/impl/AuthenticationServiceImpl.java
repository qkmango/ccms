package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.authentication.DingtalkHttpClient;
import cn.qkmango.ccms.common.authentication.GiteeHttpClient;
import cn.qkmango.ccms.common.exception.UpdateException;
import cn.qkmango.ccms.common.util.RedisUtil;
import cn.qkmango.ccms.common.util.UserSession;
import cn.qkmango.ccms.domain.bind.AuthenticationPurpose;
import cn.qkmango.ccms.domain.bind.PlatformType;
import cn.qkmango.ccms.domain.dto.AuthenticationAccount;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.domain.entity.OpenPlatform;
import cn.qkmango.ccms.domain.vo.OpenPlatformBindState;
import cn.qkmango.ccms.mvc.dao.AuthenticationDao;
import cn.qkmango.ccms.mvc.service.AuthenticationService;
import com.alibaba.fastjson2.JSONObject;
import com.aliyun.dingtalkcontact_1_0.models.GetUserResponseBody;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

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

    /**
     * Gitee授权登陆
     *
     * @param authentication 授权信息
     * @return 返回授权地址
     */
    @Override
    public String giteeAuth(AuthenticationAccount authentication) {
        // 用于第三方应用防止CSRF攻击
        String state = "auth:" + UUID.randomUUID();
        String value = JSONObject.toJSONString(authentication);

        //将 uuid 存入redis，用于第三方应用防止CSRF攻击，有效期为5分钟
        redis.set(state, value, 60 * 5);

        //登陆
        if (authentication.getPurpose() == AuthenticationPurpose.login) {
            return "https://gitee.com/oauth/authorize?response_type=code" +
                    "&client_id=" + GITEE_CLIENT_ID +
                    "&redirect_uri=" + URLEncoder.encode(GITEE_CALLBACK_LOGIN) +
                    "&state=" + state +
                    "&scope=user_info";
        }

        //绑定
        else {
            return "https://gitee.com/oauth/authorize?response_type=code" +
                    "&client_id=" + GITEE_CLIENT_ID +
                    "&redirect_uri=" + URLEncoder.encode(GITEE_CALLBACK_BIND) +
                    "&state=" + state +
                    "&scope=user_info";
        }


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
    public ModelAndView giteeLogin(String state, String code, String error, String error_description, HttpServletRequest request, Locale locale) {

        ModelAndView modelAndView = new ModelAndView();

        //设置默认值,默认为登录失败
        modelAndView.addObject("success", false);
        modelAndView.addObject("platform", "gitee");
        modelAndView.addObject("purpose", "login");
        modelAndView.setViewName("redirect:/page/common/authentication/result.html");
        modelAndView.addObject("message", messageSource.getMessage("response.authentication.failure", null, locale));


        //如果用户拒绝授权
        if (error != null || code == null) {
            return modelAndView;
        }

        //判断state是否有效，防止CSRF攻击
        String value = redis.get(state);
        redis.delete(state);
        if (value == null) {
            modelAndView.addObject("message", messageSource.getMessage("response.authentication.state.failure", null, locale));
            return modelAndView;
        }

        //获取redis中存储的授权信息,获取权限类型
        AuthenticationAccount authenticationAccount = JSONObject.parseObject(value, AuthenticationAccount.class);

        //获取用户信息
        AuthenticationPurpose purpose = authenticationAccount.getPurpose();
        JSONObject userInfo = giteeHttpClient.getUserInfoByCode(code, purpose);

        //查询数据库中是否存在该用户
        String uid = ((Integer) userInfo.get("id")).toString();
        authenticationAccount.setUid(uid);

        //判断认证用途，执行 认证 的操作
        login(authenticationAccount, modelAndView, request, locale);

        return modelAndView;
    }


    @Override
    public ModelAndView giteeBind(String state, String code, String error, String errorDescription, HttpServletRequest request, Locale locale) throws UpdateException {

        ModelAndView modelAndView = new ModelAndView();

        //设置默认值,默认为登录失败
        modelAndView.addObject("success", false);
        modelAndView.addObject("platform", "gitee");
        modelAndView.addObject("purpose", "bind");
        modelAndView.setViewName("redirect:/page/common/authentication/result.html");
        modelAndView.addObject("message", messageSource.getMessage("response.authentication.failure", null, locale));

        //如果用户拒绝授权
        if (error != null || code == null) {
            return modelAndView;
        }

        //判断state是否有效，防止CSRF攻击
        String value = redis.get(state);
        redis.delete(state);
        if (value == null) {
            modelAndView.addObject("message", messageSource.getMessage("response.authentication.state.failure", null, locale));
            return modelAndView;
        }

        //获取redis中存储的授权信息,获取权限类型
        AuthenticationAccount authenticationAccount = JSONObject.parseObject(value, AuthenticationAccount.class);

        //获取用户信息
        AuthenticationPurpose purpose = authenticationAccount.getPurpose();
        JSONObject userInfo = giteeHttpClient.getUserInfoByCode(code, purpose);
        String uid = ((Integer) userInfo.get("id")).toString();
        authenticationAccount.setUid(uid);

        //判断认证用途，执行 绑定 的操作
        Account account = UserSession.getAccount();
        OpenPlatform openPlatform = new OpenPlatform(account.getId(), PlatformType.gitee, true, uid);
        thisService.bind(openPlatform, account, locale);

        modelAndView.addObject("success", true);
        modelAndView.addObject("message", messageSource.getMessage("db.update.authentication.bind.success", null, locale));
        return modelAndView;
    }

    /**
     * 钉钉授权登陆地址
     *
     * @return 返回授权地址
     */
    @Override
    public String dingtalkAuth(AuthenticationAccount authentication) {
        String state = "auth:" + UUID.randomUUID();
        String value = JSONObject.toJSONString(authentication);
        redis.set(state, value, 60 * 5);

        //登陆
        if (authentication.getPurpose() == AuthenticationPurpose.login) {
            return "https://login.dingtalk.com/oauth2/auth?response_type=code&prompt=consent&scope=openid&redirect_uri=" + DINGTALK_CALLBACK_LOGIN +
                    "&client_id=" + DINGTALK_APP_KEY + "&state=" + state;
        }

        //绑定
        else {
            return "https://login.dingtalk.com/oauth2/auth?response_type=code&prompt=consent&scope=openid&redirect_uri=" + DINGTALK_CALLBACK_BIND +
                    "&client_id=" + DINGTALK_APP_KEY + "&state=" + state;
        }
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
    public ModelAndView dingtalkLogin(String code, String state, HttpServletRequest request, Locale locale) {

        ModelAndView modelAndView = new ModelAndView();
        //设置默认值,默认为登录失败
        modelAndView.addObject("success", false);
        modelAndView.addObject("platform", "dingtalk");
        modelAndView.addObject("purpose", "login");
        modelAndView.setViewName("redirect:/page/common/authentication/result.html");
        modelAndView.addObject("message", messageSource.getMessage("response.authentication.failure", null, locale));

        //如果用户拒绝授权
        if (code == null) {
            return modelAndView;
        }

        //判断state是否有效，防止CSRF攻击
        String value = redis.get(state);
        redis.delete(state);
        if (value == null) {
            modelAndView.addObject("message", messageSource.getMessage("response.authentication.state.failure", null, locale));
            return modelAndView;
        }

        //根据 code 获取用户信息
        GetUserResponseBody userInfo = dingtalkHttpClient.getUserInfo(code);
        if (userInfo == null) {
            return modelAndView;
        }

        //获取redis中存储的授权信息，获取权限类型
        AuthenticationAccount authenticationAccount = JSONObject.parseObject(value, AuthenticationAccount.class);
        authenticationAccount.setUid(userInfo.unionId);

        //查询数据库中是否存在该用户
        login(authenticationAccount, modelAndView, request, locale);

        return modelAndView;
    }


    @Override
    public ModelAndView dingtalkBind(String code, String state, HttpServletRequest request, Locale locale) throws UpdateException {

        ModelAndView modelAndView = new ModelAndView();

        //设置默认值,默认为登录失败
        modelAndView.addObject("success", false);
        modelAndView.addObject("platform", "gitee");
        modelAndView.addObject("purpose", "bind");
        modelAndView.setViewName("redirect:/page/common/authentication/result.html");
        modelAndView.addObject("message", messageSource.getMessage("response.authentication.failure", null, locale));

        //如果用户拒绝授权
        //如果用户拒绝授权
        if (code == null) {
            return modelAndView;
        }

        //判断state是否有效，防止CSRF攻击
        String value = redis.get(state);
        redis.delete(state);
        if (value == null) {
            modelAndView.addObject("message", messageSource.getMessage("response.authentication.state.failure", null, locale));
            return modelAndView;
        }

        //获取redis中存储的授权信息,获取权限类型
        // AuthenticationAccount authenticationAccount = JSONObject.parseObject(value, AuthenticationAccount.class);

        //获取用户信息
        GetUserResponseBody userInfo = dingtalkHttpClient.getUserInfo(code);
        String uid = userInfo.getUnionId();

        //判断认证用途，执行 绑定 的操作
        Account account = UserSession.getAccount();
        OpenPlatform openPlatform = new OpenPlatform(account.getId(), PlatformType.dingtalk, true, uid);
        thisService.bind(openPlatform, account, locale);

        modelAndView.addObject("success", true);
        modelAndView.addObject("message", messageSource.getMessage("db.update.authentication.bind.success", null, locale));
        return modelAndView;
    }


    /**
     * 获取开放平台绑定状态
     *
     * @return 返回开放平台绑定状态
     */
    @Override
    public OpenPlatformBindState openPlatformBindState() {
        Account account = UserSession.getAccount();
        return dao.openPlatformBindState(account);
    }


    /**
     * 授权
     *
     * @param account 认证账户
     */
    private void login(AuthenticationAccount account,
                       ModelAndView modelAndView,
                       HttpServletRequest request,
                       Locale locale) {

        Account loginAccount = dao.authentication(account);
        //如果不存在，返回错误信息
        if (loginAccount == null) {
            PlatformType platform = account.getPlatform();
            String msgKey = null;
            switch (platform) {
                case gitee -> msgKey = "db.user.gitee.uid.failure@notExist";
                case dingtalk -> msgKey = "db.user.dingtalk.uid.failure@notExist";
            }
            modelAndView.addObject("message", messageSource.getMessage(msgKey, null, locale));
            return;
        }

        //登陆成功, 添加session ,返回用户信息
        loginAccount.setPermissionType(account.getPermission());
        request.getSession(true).setAttribute("account", loginAccount);

        modelAndView.addObject("account", loginAccount);
        modelAndView.addObject("success", true);
        modelAndView.addObject("message", messageSource.getMessage("response.authentication.success", null, locale));
    }

    /**
     * 绑定第三方账户
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void bind(OpenPlatform openPlatform,
                     Account account,
                     Locale locale) throws UpdateException {

        int affectedRows = dao.bind(openPlatform, account);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.update.authentication.bind.failure", null, locale));
        }

    }
}
