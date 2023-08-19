package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.domain.bo.AccountPayQrcode;
import cn.qkmango.ccms.domain.dto.QrCodeConsume;
import cn.qkmango.ccms.domain.dto.TradeQueryDto;
import cn.qkmango.ccms.domain.dto.TradeRefundDto;
import cn.qkmango.ccms.domain.entity.Trade;
import cn.qkmango.ccms.domain.pagination.PageData;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.domain.vo.TradeVO;

/**
 * 交易
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-22 13:44
 */
public interface TradeService {
    // void insert(Trade trade, Locale locale);

    PageData<Trade> list(Pagination<TradeQueryDto> pagination);

    Trade record(Long id);

    TradeVO detail(Long id);

    R refund(TradeRefundDto dto);

    AccountPayQrcode createQrCode(Integer account);

    R consumeByQrCode(QrCodeConsume consume);
}
