package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.TradeTimeout;
import org.apache.ibatis.annotations.Mapper;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-24 20:42
 */
@Mapper
public interface TradeTimeoutDao extends BaseDao<TradeTimeout, Integer> {

    @Override
    TradeTimeout getById(Integer id);

    TradeTimeout getRecordByTradeId(Long trade);

    int deleteByTradeId(Long trade);

    @Override
    int insert(TradeTimeout record);
}
