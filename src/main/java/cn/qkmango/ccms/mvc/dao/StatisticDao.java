package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.dto.ValidListDto;
import cn.qkmango.ccms.domain.entity.ConsumeStatistic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
    List<ConsumeStatistic> consumeCountPriceByDayAndType(ValidListDto.DatetimeRange range);

    /**
     * 获取指定时间范围内的消费信息
     * 统计学生指定时间的消费次数、总额、每次平均消费额
     * count    消费次数
     * sum      总额
     * avg      平均消费额
     * max      最大消费额
     *
     * @param id    学生id
     * @param range 时间范围
     * @return 当前月消费信息聚合数据
     */
    // @MapKey("id")
    Map<String, Integer> consumeInfo(@Param("id") String id,
                                     @Param("range") ValidListDto.DatetimeRange range);

    /**
     * 用户每类消费金额饼图
     * 不包含 缴费PAYMENT,退款REFUND,充值RECHARGE
     *
     * @param id    学生id
     * @param range 开始时间和结束时间范围
     * @return 一个月每类消费金额数据
     */
    List<Map<String, Integer>> consumePriceByType(@Param("id") String id,
                                                  @Param("range") ValidListDto.DatetimeRange range);

    /**
     * 消费统计数据
     * 按每天和消费类型统计消费金额和消费次数
     *
     * @param list 消费统计数据
     * @return int 影响的记录数
     */
    int insertConsumeStatistics(List<ConsumeStatistic> list);

    /**
     * 消费统计数据
     *
     * @param range 开始时间和结束时间范围
     * @return 消费统计数据
     */
    List<ConsumeStatistic> consumeStatistic(ValidListDto.DatetimeRange range);
}
