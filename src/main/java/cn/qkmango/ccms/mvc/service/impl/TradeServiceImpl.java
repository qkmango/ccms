package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.domain.entity.Trade;
import cn.qkmango.ccms.mvc.dao.TradeDao;
import cn.qkmango.ccms.mvc.service.TradeService;
import jakarta.annotation.Resource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-07-22 13:44
 */
@Service
public class TradeServiceImpl implements TradeService {

    @Resource
    private TradeDao dao;

    @Resource
    private ReloadableResourceBundleMessageSource ms;

    @Override
    public void insert(Trade trade, Locale locale) {

    }
}
