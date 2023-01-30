package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.util.DateTimeUtil;
import cn.qkmango.ccms.domain.param.DatetimeRange;
import cn.qkmango.ccms.domain.vo.statistic.ConsumePriceCount;
import cn.qkmango.ccms.mvc.dao.StatisticDao;
import cn.qkmango.ccms.mvc.service.StatisticService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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
@Service
public class StatisticServiceImpl implements StatisticService {

    @Resource
    private StatisticDao dao;

    /**
     * 统计最近一周消费金额数据
     *
     * @param range 开始时间和结束时间范围
     *              如果不传入时间，则默认统计最近一周的数据
     *              如果传入时间，则统计传入时间段的数据
     *              如果开始时间为空，则默认设置为结束时间的前一周
     *              如果结束时间为空，则默认设置为开始时间的后一周
     * @return 最近一周消费金额和消费次数
     */
    @Override
    public List<ConsumePriceCount> ConsumeCountPriceOfDay(DatetimeRange range) {
        //如果入参为空，则默认统计最近一周的数据
        if (range == null) {
            range = new DatetimeRange();
        }

        Date startTime = range.getStartTime();
        Date endTime = range.getEndTime();

        //如果都为空，则默认统计最近一周的数据
        if (startTime == null && endTime == null) {
            Calendar calendar = DateTimeUtil.addDay(null, -6);
            range.setStartTime(calendar.getTime());
        }
        //如果开始时间为空，则默认设置为结束时间的前一周
        else if (startTime == null) {
            Calendar calendar = DateTimeUtil.addDay(endTime, -6);
            range.setStartTime(calendar.getTime());
        }
        //如果结束时间为空，则默认设置为开始时间的后一周
        else if (endTime == null) {
            Calendar calendar = DateTimeUtil.addDay(startTime, 6);
            range.setEndTime(calendar.getTime());
        }

        return dao.ConsumeCountPriceOfDay(range);
    }
}
