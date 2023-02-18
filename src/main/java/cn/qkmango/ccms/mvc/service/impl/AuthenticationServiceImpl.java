package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.authentication.GiteeHttpClient;
import cn.qkmango.ccms.common.util.RedisUtil;
import cn.qkmango.ccms.domain.bind.PermissionType;
import cn.qkmango.ccms.domain.entity.Account;
import cn.qkmango.ccms.mvc.dao.AuthenticationDao;
import cn.qkmango.ccms.mvc.service.AuthenticationService;
import com.alibaba.fastjson2.JSONObject;
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

    @Value("${ccms.authentication.gitee.clientId}")
    private String GITEE_CLIENT_ID;

    @Value("${ccms.authentication.gitee.callback}")
    private String GITEE_CALLBACK;

    @Resource
    private AuthenticationDao dao;

    @Resource(name = "redisUtil")
    private RedisUtil redis;

    @Resource
    private GiteeHttpClient client;

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    /**
     * Gitee授权登陆
     *
     * @param type 权限类型
     * @return 返回授权地址
     */
    @Override
    public String giteeAuth(PermissionType type) {
        // 用于第三方应用防止CSRF攻击
        String state = type + ":" + UUID.randomUUID();

        // Step1：获取Authorization Code
        String redirect = "https://gitee.com/oauth/authorize?response_type=code" +
                "&client_id=" + GITEE_CLIENT_ID +
                "&redirect_uri=" + URLEncoder.encode(GITEE_CALLBACK) +
                "&state=" + state +
                "&scope=user_info&type=" + type;

        //将 uuid 存入redis，用于第三方应用防止CSRF攻击，有效期为5分钟
        redis.set("authentication:gitee:state:" + state, "", 60 * 5);

        return redirect;
    }


    /**
     * Gitee授权回调
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
        modelAndView.setViewName("redirect:/page/authentication/gitee/error.html");

        //如果用户拒绝授权
        if (error != null || code == null) {
            modelAndView.addObject("message", messageSource.getMessage("response.authentication.failure", null, locale));
            return modelAndView;
        }


        //判断state是否有效，防止CSRF攻击
        String stateKey = "authentication:gitee:state:" + state;
        boolean has = redis.hasKey(stateKey);
        redis.delete(stateKey);
        if (!has) {
            modelAndView.addObject("message", messageSource.getMessage("response.authentication.state.failure", null, locale));
            return modelAndView;
        }

        //获取Access Token
        JSONObject json = client.getAccessToken(code);
        String accessToken = (String) json.get("access_token");
        //获取用户信息
        JSONObject userInfo = client.getUserInfo(accessToken);
        String uid = ((Integer) userInfo.get("id")).toString();
        PermissionType type = PermissionType.valueOf(state.split(":")[0]);

        //查询数据库中是否存在该用户
        switch (type) {
            case user -> loginAccount = dao.userAuthentication(uid);
            case admin -> loginAccount = dao.adminAuthentication(uid);
        }
        //如果不存在，返回错误信息
        if (loginAccount == null) {
            modelAndView.addObject("message", messageSource.getMessage("db.user.gitee.uid.failure@notExist", null, locale));
            return modelAndView;
        }


        //登陆成功, 添加session ,返回用户信息
        loginAccount.setPermissionType(type);
        request.getSession(true).setAttribute("account", loginAccount);

        modelAndView.addObject("account", loginAccount);
        modelAndView.addObject("success", true);
        modelAndView.addObject("message", messageSource.getMessage("db.user.gitee.uid.failure@notExist", null, locale));
        modelAndView.setViewName("redirect:/page/authentication/gitee/success.html");

        return modelAndView;
    }


}
