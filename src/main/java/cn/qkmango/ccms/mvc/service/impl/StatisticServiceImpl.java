package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.common.util.DateTimeUtil;
import cn.qkmango.ccms.domain.entity.ConsumeStatistic;
import cn.qkmango.ccms.domain.param.DatetimeRange;
import cn.qkmango.ccms.mvc.dao.StatisticDao;
import cn.qkmango.ccms.mvc.service.StatisticService;
import jakarta.annotation.Resource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    /**
     * 统计最近一周消费金额数据
     * <p>
     * 按照一天和消费类型统计消费金额
     *
     * @param range 开始时间和结束时间范围
     *              如果不传入时间，则默认统计最近一周的数据
     *              如果传入时间，则统计传入时间段的数据
     *              如果开始时间为空，则默认设置为结束时间的前一周
     *              如果结束时间为空，则默认设置为开始时间的后一周
     * @return 最近一周消费金额和消费次数
     */
    @Override
    public List<ConsumeStatistic> consumeStatistic(DatetimeRange range) {
        //如果入参为空，则默认统计最近一周的数据
        if (range == null) {
            range = new DatetimeRange();
        }

        Date startTime = range.getStartTime();
        Date endTime = range.getEndTime();

        //如果都为空，则默认统计最近一周的数据
        if (startTime == null && endTime == null) {
            //设置起始时间为当天的开始时间
            Calendar calendar = DateTimeUtil.getDayStart(null);
            DateTimeUtil.addDay(calendar, -6);
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

        return dao.consumeStatistic(range);
    }

    /**
     * 插入消费统计数据
     *
     * @param list 消费统计数据
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertConsumeStatistics(List<ConsumeStatistic> list) throws InsertException {
        int affectedRows = dao.insertConsumeStatistics(list);
        if (affectedRows != list.size()) {
            throw new InsertException(messageSource.getMessage("db.insert.consume.statistic.success", null, Locale.getDefault()));
        }
    }

    /**
     * 统计每天的消费金额和消费次数
     * <p>
     * 按照一天和消费类型统计消费金额
     *
     * @param range 开始时间和结束时间范围
     * @return 每天的消费金额和消费次数
     */
    @Override
    public List<ConsumeStatistic> consumeCountPriceByDayAndType(DatetimeRange range) {
        return dao.consumeCountPriceByDayAndType(range);
    }
}
