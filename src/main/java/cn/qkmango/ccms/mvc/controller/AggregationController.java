package cn.qkmango.ccms.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 聚合接口
 * <p>提供整个页面的一次请求所需的数据
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-07 20:25
 */
@RestController
@RequestMapping("aggregation")
public class AggregationController {

//    @Resource
//    private AggregationService service;
//
//    /**
//     * 用户欢迎界面聚合数据
//     *
//     * @return 用户首页所需的数据
//     */
//    @Permission(Role.user)
//    @GetMapping("user/welcome.do")
//    public R<Map<String, Object>> userWelcome() {
//        String id = AccountHolder.getAccountId();
//        Map<String, Object> data = service.userWelcome(id);
//        return R.success(data);
//    }
}
