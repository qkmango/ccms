package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.bind.trade.TradeState;
import cn.qkmango.ccms.domain.dto.TradeQueryDto;
import cn.qkmango.ccms.domain.entity.Trade;
import cn.qkmango.ccms.domain.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 交易Dao
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-22 13:45
 */
@Mapper
public interface TradeDao extends BaseDao<Trade, Long> {
    int insert(Trade trade);

    @Override
    Trade getById(Long id);

    List<Trade> list(Pagination<TradeQueryDto> pagination);

    int updateState(Long id, TradeState state, Integer version);

    int payed(Long id, String outId, Integer version);
}
