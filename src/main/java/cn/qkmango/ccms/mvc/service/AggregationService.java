package cn.qkmango.ccms.mvc.service;

import java.util.Map;

/**
 * 聚合接口
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-12-07 20:26
 */
public interface AggregationService {
    /**
     * 用户欢迎界面聚合数据
     *
     * @param id 用户id
     * @return 用户首页所需的数据
     */
    Map<String, Object> userWelcome(String id);
}
