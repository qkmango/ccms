package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.util.SnowFlake;
import cn.qkmango.ccms.domain.bind.CardState;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel1;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel2;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel3;
import cn.qkmango.ccms.domain.bind.trade.TradeState;
import cn.qkmango.ccms.domain.dto.CardRechargeDto;
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
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Locale;


/**
 * 卡
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
    private TransactionTemplate tx;

    @Resource
    private ReloadableResourceBundleMessageSource ms;

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
     */
    @Override
    public R recharge(CardRechargeDto dto) throws UpdateException {
        Locale locale = LocaleContextHolder.getLocale();
        Integer account = dto.getAccount();
        Integer amount = dto.getAmount();

        Card card = cardDao.getRecordByAccount(account);

        // 判断卡是否存在
        if (card == null) {
            throw new UpdateException(ms.getMessage("db.account.failure@notExist", null, locale));
        }
        // 判断卡状态是否正常
        if (card.getState() != CardState.normal) {
            throw new UpdateException(ms.getMessage("db.card.failure@state", null, locale));
        }


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

        R result = tx.execute(status -> {
            int affectedRows;

            // 插入交易记录(充值)
            affectedRows = tradeDao.insert(trade);
            if (affectedRows != 1) {
                status.setRollbackOnly();
                return R.fail(ms.getMessage("db.update.recharge.failure", null, locale));
            }

            // 更新卡余额
            affectedRows = cardDao.addBalance(account, amount, card.getVersion());
            if (affectedRows != 1) {
                status.setRollbackOnly();
                return R.fail(ms.getMessage("db.update.recharge.failure", null, locale));
            }
            return R.success(ms.getMessage("db.update.recharge.success", null, locale));
        });

        return result;
    }

    @Override
    public void state(Integer account, CardState state) throws UpdateException {
        int affectedRows = cardDao.state(account, state);
        if (affectedRows != 1) {
            throw new UpdateException(ms.getMessage("db.card.update.state.failure", null, LocaleContextHolder.getLocale()));
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
