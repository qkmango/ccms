package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.authentication.DingtalkHttpClient;
import cn.qkmango.ccms.common.authentication.GiteeHttpClient;
import cn.qkmango.ccms.common.util.RedisUtil;
import cn.qkmango.ccms.domain.bind.PermissionType;
import cn.qkmango.ccms.domain.dto.Authentication;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.mvc.dao.AuthenticationDao;
import cn.qkmango.ccms.mvc.service.AuthenticationService;
import com.alibaba.fastjson2.JSONObject;
import com.aliyun.dingtalkcontact_1_0.models.GetUserResponseBody;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
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
    @Value("${ccms.authentication.gitee.callback}")
    private String GITEE_CALLBACK;

    // 钉钉授权登陆
    @Value("${ccms.authentication.dingtalk.callback}")
    private String DINGTALK_CALLBACK;
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

    /**
     * Gitee授权登陆
     *
     * @param authentication 授权信息
     * @return 返回授权地址
     */
    @Override
    public String giteeAuth(Authentication authentication) {
        // 用于第三方应用防止CSRF攻击
        String state = "auth:" + UUID.randomUUID();
        String value = JSONObject.toJSONString(authentication);

        // Step1：获取Authorization Code
        String redirect = "https://gitee.com/oauth/authorize?response_type=code" +
                "&client_id=" + GITEE_CLIENT_ID +
                "&redirect_uri=" + URLEncoder.encode(GITEE_CALLBACK) +
                "&state=" + state +
                "&scope=user_info";

        //将 uuid 存入redis，用于第三方应用防止CSRF攻击，有效期为5分钟
        redis.set(state, value, 60 * 5);

        return redirect;
    }


    /**
     * Gitee授权回调
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
    public ModelAndView giteeCallback(String state, String code, String error, String error_description, HttpServletRequest request, Locale locale) {

        ModelAndView modelAndView = new ModelAndView();
        Account loginAccount = null;

        //设置默认值,默认为登录失败
        modelAndView.addObject("success", false);
        modelAndView.addObject("type", "gitee");
        modelAndView.setViewName("redirect:/page/common/authentication.html");
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

        //获取Access Token
        JSONObject json = giteeHttpClient.getAccessToken(code);
        String accessToken = (String) json.get("access_token");
        //获取用户信息
        JSONObject userInfo = giteeHttpClient.getUserInfo(accessToken);

        //获取redis中存储的授权信息,获取权限类型
        Authentication authentication = JSONObject.parseObject(value, Authentication.class);
        PermissionType permission = authentication.getPermission();

        //查询数据库中是否存在该用户
        String uid = ((Integer) userInfo.get("id")).toString();
        authentication.setUid(uid);
        loginAccount = dao.authentication(authentication);
        //如果不存在，返回错误信息
        if (loginAccount == null) {
            modelAndView.addObject("message", messageSource.getMessage("db.user.gitee.uid.failure@notExist", null, locale));
            return modelAndView;
        }


        //登陆成功, 添加session ,返回用户信息
        loginAccount.setPermissionType(permission);
        request.getSession(true).setAttribute("account", loginAccount);

        modelAndView.addObject("account", loginAccount);
        modelAndView.addObject("success", true);
        modelAndView.addObject("message", messageSource.getMessage("response.authentication.success", null, locale));

        return modelAndView;
    }


    /**
     * 钉钉授权登陆地址
     *
     * @return 返回授权地址
     */
    @Override
    public String dingtalkAuth(Authentication authentication) {
        String state = "auth:" + UUID.randomUUID();
        String value = JSONObject.toJSONString(authentication);
        redis.set(state, value, 60 * 5);

        return "https://login.dingtalk.com/oauth2/auth?response_type=code&prompt=consent&scope=openid&redirect_uri=" + DINGTALK_CALLBACK +
                "&client_id=" + DINGTALK_APP_KEY + "&state=" + state;
    }


    /**
     * 钉钉回调地址
     * 回调中进行从钉钉获取用户信息，然后和系统数据库进行比对登陆
     *
     * @param code  授权码
     * @param state 授权状态,防止CSRF攻击,授权状态,防止CSRF攻击
     * @return 返回重定向页面
     */
    @Override
    public ModelAndView dingtalkCallback(String code, String state, HttpServletRequest request, Locale locale) {

        ModelAndView modelAndView = new ModelAndView();
        Account loginAccount = null;

        //设置默认值,默认为登录失败
        modelAndView.addObject("success", false);
        modelAndView.addObject("type", "dingtalk");
        modelAndView.setViewName("redirect:/page/common/authentication.html");
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
        Authentication authentication = JSONObject.parseObject(value, Authentication.class);
        PermissionType permission = authentication.getPermission();

        //查询数据库中是否存在该用户
        authentication.setUid(userInfo.unionId);
        loginAccount = dao.authentication(authentication);

        //如果不存在，返回错误信息
        if (loginAccount == null) {
            modelAndView.addObject("message", messageSource.getMessage("db.user.dingtalk.uid.failure@notExist", null, locale));
            return modelAndView;
        }


        //登陆成功, 添加session ,返回用户信息
        loginAccount.setPermissionType(permission);
        request.getSession(true).setAttribute("account", loginAccount);

        modelAndView.addObject("account", loginAccount);
        modelAndView.addObject("success", true);
        modelAndView.addObject("message", messageSource.getMessage("response.authentication.success", null, locale));

        return modelAndView;
    }
}
