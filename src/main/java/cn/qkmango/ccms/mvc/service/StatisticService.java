package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.domain.param.DatetimeRange;
import cn.qkmango.ccms.domain.vo.statistic.ConsumePriceCount;

import java.util.Date;
import java.util.List;

/**
 * 统计分析
 * 数据统计分析
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-29 18:06
 */
public interface StatisticService {
    /**
     * 统计最近一周消费金额数据
     *
     * @param range 开始时间和结束时间范围
     *              如果不传入时间，则默认统计最近一周的数据
     * @return 最近一周消费金额和消费次数
     */
    List<ConsumePriceCount> ConsumeCountPriceOfDay(DatetimeRange range);
}
