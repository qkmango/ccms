package cn.qkmango.ccms.schedule;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 消费统计定时任务
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-05 18:07
 */
//@Component
public class StatisticSchedule {


    private static final Logger logger = Logger.getLogger(StatisticSchedule.class);


    /**
     * 每天凌晨 1 点执行一次，统计前一天的消费数据
     */
    @Scheduled(cron = "0 0 1 * * ?")
    private void consumeStatistic() {
    }

}
