package cn.qkmango.ccms.mvc.service;

import cn.qkmango.ccms.domain.entity.Trade;

import java.util.Locale;

/**
 * 交易
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-22 13:44
 */
public interface TradeService {
    void insert(Trade trade, Locale locale);
}
