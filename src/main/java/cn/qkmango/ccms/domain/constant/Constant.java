package cn.qkmango.ccms.domain.constant;

/**
 * 系统常量
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-30 15:57
 */
public class Constant {
    /**
     * Pos刷卡机默认密码
     */
    public static final String POS_DEFAULT_PASSWORD = "888888";

    /**
     * 登陆接口
     */
    public static final String LOGIN_URL = "/account/login.do";

    /**
     * 无权操作
     * json格式
     * Operation without permission
     */
    public static final String OPERATION_WITHOUT_PERMISSION_JSON = "{\"success\":false,\"message\":\"无权操作\"}";

    /**
     * 未登录
     * json格式
     */
    public static final String NOT_LOGIN_JSON = "{\"success\":false,\"message\":\"未登录\"}";
}
