package cn.qkmango.ccms.schedule;

import cn.qkmango.ccms.common.exception.database.InsertException;
import cn.qkmango.ccms.common.util.DateTimeUtil;
import cn.qkmango.ccms.domain.bind.ConsumeStatisticType;
import cn.qkmango.ccms.domain.dto.ValidListDto;
import cn.qkmango.ccms.domain.entity.ConsumeStatistic;
import cn.qkmango.ccms.mvc.dao.StatisticDao;
import cn.qkmango.ccms.mvc.service.StatisticService;
import jakarta.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;
import java.util.List;

/**
 * 消费统计定时任务
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-05 18:07
 */
//@Component
public class StatisticSchedule {

    @Resource
    private StatisticService statisticService;

    @Resource
    private StatisticDao statisticDao;

    private static final Logger logger = Logger.getLogger(StatisticSchedule.class);


    /**
     * 每天凌晨 1 点执行一次，统计前一天的消费数据
     */
    @Scheduled(cron = "0 0 1 * * ?")
    private void consumeStatistic() {

        //设置统计时间范围,前一天
        ValidListDto.DatetimeRange range = new ValidListDto.DatetimeRange();
        Calendar calendar = DateTimeUtil.addDay(Calendar.getInstance(), -1);

        Calendar start = DateTimeUtil.getDayStart(calendar);
        range.setStartTime(start.getTime());
        Calendar end = DateTimeUtil.getDayEnd(calendar);
        range.setEndTime(end.getTime());

        //统计前一天的消费数据
        List<ConsumeStatistic> list = statisticService.consumeCountPriceByDayAndType(range);

        if (list == null || list.size() == 0) {
            logger.info("前一天无消费数据");
            return;
        }

        //将数据累加获取前一天的总消费金额和消费次数
        ConsumeStatistic dayAll = new ConsumeStatistic();
        dayAll.setDate(calendar.getTime());
        dayAll.setType(ConsumeStatisticType.ALL);
        dayAll.setPrice(0);
        dayAll.setCount(0);

        for (ConsumeStatistic item : list) {
            dayAll.setPrice(dayAll.getPrice() + item.getPrice());
            dayAll.setCount(dayAll.getCount() + item.getCount());
        }

        list.add(dayAll);

        //将统计数据存入数据库
        try {
            statisticService.insertConsumeStatistics(list);
        } catch (InsertException e) {
            logger.warn(e.getMessage());
        }
        logger.info("统计前一天的消费数据成功");
    }

}
