package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.entity.Trade;
import org.apache.ibatis.annotations.Mapper;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-22 13:45
 */
@Mapper
public interface TradeDao extends BaseDao<Trade, Long> {
    int insert(Trade trade);
}
