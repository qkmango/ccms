package cn.qkmango.ccms.mvc.controller;

import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.param.DatetimeRange;
import cn.qkmango.ccms.domain.vo.statistic.ConsumePriceCount;
import cn.qkmango.ccms.mvc.service.StatisticService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

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
     * 统计最费金额数据
     *
     * @param range 开始时间和结束时间范围
     *              如果不传入时间，则默认统计最近一周的数据
     * @return 最近一周消费金额和消费次数
     */
    @GetMapping("/consume/count-price-of-day.do")
    public R<List> ConsumeCountPriceOfDay(DatetimeRange range) {
        List<ConsumePriceCount> date = service.ConsumeCountPriceOfDay(range);
        return R.success(date);
    }
}
