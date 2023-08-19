package cn.qkmango.ccms.mvc.dao;

import cn.qkmango.ccms.domain.dto.TradeQueryDto;
import cn.qkmango.ccms.domain.entity.Trade;
import cn.qkmango.ccms.domain.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    @Override
    Trade getRecordById(Long id);

    List<Trade> list(Pagination<TradeQueryDto> pagination);

    int refund(Long id, Integer version);
}
