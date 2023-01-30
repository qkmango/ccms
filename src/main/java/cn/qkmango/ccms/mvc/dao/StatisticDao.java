package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.param.DatetimeRange;
import cn.qkmango.ccms.domain.vo.statistic.ConsumePriceCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 统计分析
 * 数据统计分析
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-01-29 18:06
 */
@Mapper
public interface StatisticDao {
    int count();

    /**
     * 统计每天的消费金额和消费次数
     * <p>
     * 按照一天和消费类型统计消费金额
     *
     * @param range 开始时间和结束时间范围
     * @return 每天的消费金额和消费次数
     */
    List<ConsumePriceCount> ConsumeCountPriceByDayAndType(DatetimeRange range);

}
