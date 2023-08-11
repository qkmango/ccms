package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.util.SnowFlake;
import cn.qkmango.ccms.domain.bind.CardState;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel1;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel2;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel3;
import cn.qkmango.ccms.domain.bind.trade.TradeState;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.entity.Trade;
import cn.qkmango.ccms.domain.pagination.PageData;
import cn.qkmango.ccms.domain.pagination.Pagination;
import cn.qkmango.ccms.mvc.dao.CardDao;
import cn.qkmango.ccms.mvc.dao.TradeDao;
import cn.qkmango.ccms.mvc.service.CardService;
import jakarta.annotation.Resource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;


/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2022-10-22 20:14
 */
@Service
public class CardServiceImpl implements CardService {

    @Resource
    private CardDao cardDao;

    @Resource
    private TradeDao tradeDao;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private ReloadableResourceBundleMessageSource messageSource;

    /**
     * 分页查询卡信息
     *
     * @param pagination 分页查询条件
     * @return 分页查询结果
     */
    @Override
    public PageData<Card> list(Pagination<Card> pagination) {
        List<Card> list = cardDao.list(pagination);
        int count = cardDao.count();
        return PageData.of(list, count);
    }

    /**
     * 充值
     *
     * @param account 账户
     * @param amount  充值金额
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void recharge(Integer account, Integer amount, Locale locale) throws UpdateException {

        Card record = cardDao.getRecordByAccount(account);

        //判断卡是否存在
        if (record == null) {
            throw new UpdateException(messageSource.getMessage("db.account.failure@notExist", null, locale));
        }
        //判断卡状态是否正常
        if (record.getState() != CardState.normal) {
            throw new UpdateException(messageSource.getMessage("db.card.failure@state", null, locale));
        }


        int affectedRows = 0;

        Trade trade = new Trade()
                .setId(snowFlake.nextId())
                .setAccount(account)
                .setLevel1(TradeLevel1.in)
                .setLevel2(TradeLevel2.system)
                .setLevel3(TradeLevel3.recharge)
                .setState(TradeState.success)
                .setAmount(amount)
                .setCreateTime(System.currentTimeMillis())
                .setVersion(0);

        //插入消费记录(充值)
        affectedRows = tradeDao.insert(trade);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.update.recharge.failure", null, locale));
        }

        //更新卡余额
        affectedRows = cardDao.recharge(account, amount);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.update.recharge.failure", null, locale));
        }
    }

    @Override
    public void state(Integer account, CardState state) throws UpdateException {
        int affectedRows = cardDao.state(account, state);
        if (affectedRows != 1) {
            throw new UpdateException(messageSource.getMessage("db.card.update.state.failure", null, LocaleContextHolder.getLocale()));
        }
    }

    /**
     * 根据账户ID查询卡信息
     *
     * @param account
     * @return 卡详细信息
     */
    @Override
    public Card recordByAccount(Integer account) {
        return cardDao.getRecordByAccount(account);
    }

}
