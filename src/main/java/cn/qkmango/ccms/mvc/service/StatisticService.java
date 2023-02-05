package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.exception.InsertException;
import cn.qkmango.ccms.domain.entity.ConsumeStatistic;
import cn.qkmango.ccms.domain.param.DatetimeRange;

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
     * <p>
     * 按照一天和消费类型统计消费金额
     *
     * @param range 开始时间和结束时间范围
     *              如果不传入时间，则默认统计最近一周的数据
     * @return 最近一周消费金额和消费次数
     */
    List<ConsumeStatistic> ConsumeCountPriceByDayAndType(DatetimeRange range);


    /**
     * 插入消费统计数据
     *
     * @param list 消费统计数据
     */
    void insertConsumeStatistics(List<ConsumeStatistic> list) throws InsertException;
}
