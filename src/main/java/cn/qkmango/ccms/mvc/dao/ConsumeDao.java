package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.Consume;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.param.ConsumeParam;
import cn.qkmango.ccms.domain.vo.ConsumeDetailsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消费表 ConsumeDao
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-29 23:04
 */
@Mapper
public interface ConsumeDao {
    /**
     * 消费Dao层接口
     *
     * @param consume 消费信息
     * @return 数据库影响的行数
     */
    int insert(Consume consume);

    /**
     * 获取未分页的总记录条数
     *
     * @return 总条数
     */
    int getCount();


    /**
     * 分页查询消费记录
     *
     * @param pagination 分页查询条件
     * @return
     */
    List<Consume> queryConsumePagination(@Param("pagination") Pagination<ConsumeParam> pagination);

    /**
     * 获取当前月消费信息
     * 统计学生当前月的消费次数、总额、每次平均消费额
     * currMonthConsumeCount 消费次数
     * currMonthConsumeMoney 总额
     * currMonthConsumeAvg   平均消费额
     * currMonthConsumeMax 最大消费额
     *
     * @param id 学生id
     * @return 当前月消费信息聚合数据
     */
    HashMap<String, Integer> currMonthConsumeInfo(String id);


    /**
     * 每类消费金额饼图
     * 不包含 缴费PAYMENT,退款REFUND,充值RECHARGE
     *
     * @param id 学生id
     * @return 每类消费金额饼图数据
     */
    List<Map<String, Integer>> currMonthConsumeTypePricePie(String id);

    /**
     * 查询消费记录详情
     *
     * @param consume 消费信息(ID)
     * @return 消费详细信息
     */
    ConsumeDetailsVO detail(Consume consume);
}
