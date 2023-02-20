package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bind.AuthenticationPurpose;
import cn.qkmango.ccms.domain.bind.AuthenticationType;
import cn.qkmango.ccms.domain.bind.PermissionType;
import cn.qkmango.ccms.domain.dto.Authentication;
import cn.qkmango.ccms.mvc.service.AuthenticationService;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

/**
 * 第三方授权登陆
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-18 18:10
 */
@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    @Resource
    private AuthenticationService service;


    /**
     * Gitee授权登陆
     *
     * @param purpose 授权目的
     * @param locale  语言环境
     * @return 返回授权地址
     */
    @ResponseBody
    @RequestMapping("gitee/auth.do")
    @Permission({PermissionType.admin, PermissionType.user})
    public R giteeAuth(@RequestParam PermissionType permission,
                       @RequestParam AuthenticationPurpose purpose,
                       Locale locale) {

        Authentication authentication = new Authentication();
        authentication.setPurpose(purpose);
        authentication.setPermission(permission);
        authentication.setType(AuthenticationType.gitee);

        String redirect = service.giteeAuth(authentication);
        return R.success().setData(redirect);
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
    @RequestMapping("gitee/callback.do")
    public ModelAndView giteeCallback(@RequestParam String state,
                                      String code,
                                      String error,
                                      String error_description,
                                      HttpServletRequest request,
                                      Locale locale) {

        return service.giteeCallback(state, code, error, error_description, request, locale);
    }


    /**
     * 钉钉授权登陆地址
     *
     * @return 返回授权地址
     */
    @ResponseBody
    @RequestMapping("dingtalk/auth.do")
    @Permission({PermissionType.admin, PermissionType.user})
    public R dingtalkAuth(@RequestParam PermissionType permission,
                          @RequestParam AuthenticationPurpose purpose,
                          Locale locale) {
        Authentication authentication = new Authentication();
        authentication.setPurpose(purpose);
        authentication.setPermission(permission);
        authentication.setType(AuthenticationType.gitee);

        String redirect = service.dingtalkAuth(authentication);
        return R.success().setData(redirect);
    }


    /**
     * 钉钉回调地址
     */
    @RequestMapping("dingtalk/callback.do")
    public ModelAndView dingtalkCallback(String code, String state, HttpServletRequest request, Locale locale) {
        return service.dingtalkCallback(code, state, request, locale);
    }

}
