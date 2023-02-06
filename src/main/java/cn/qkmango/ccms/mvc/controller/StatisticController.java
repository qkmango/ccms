package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.annotation.Permission;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bind.PermissionType;
import cn.qkmango.ccms.domain.entity.ConsumeStatistic;
import cn.qkmango.ccms.domain.param.DatetimeRange;
import cn.qkmango.ccms.mvc.service.StatisticService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 统计分析控制器
 * 数据统计分析
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-29 17:54
 */
@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Resource
    private StatisticService service;

    /**
     * 统计消费数据
     * <p>
     * 按照一天和消费类型统计消费金额
     *
     * @param range 开始时间和结束时间范围
     *              如果不传入时间，则默认统计最近一周的数据
     * @return 最近一周消费金额和消费次数
     */
    @Permission(PermissionType.admin)
    @GetMapping("/consume/statistic-price-by-day-and-type.do")
    public R<Map> consumeStatistic(DatetimeRange range) {
        Map<Long, List<ConsumeStatistic>> date = service.consumeStatistic(range);
        return R.success(date);
    }

}
