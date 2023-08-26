package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.map.R;
import cn.qkmango.ccms.common.util.SnowFlake;
import cn.qkmango.ccms.domain.bind.CardState;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel1;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel2;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel3;
import cn.qkmango.ccms.domain.bind.trade.TradeState;
import cn.qkmango.ccms.domain.dto.CanceledDto;
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

        // 1. 检查卡
        Card card = cardDao.getByAccount(account);
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

            // 2. 插入交易记录(充值)
            affectedRows = tradeDao.insert(trade);
            if (affectedRows != 1) {
                status.setRollbackOnly();
                return R.fail(ms.getMessage("db.update.recharge.failure", null, locale));
            }

            // 3. 更新卡余额
            affectedRows = cardDao.addBalance(account, amount, dto.getVersion());
            if (affectedRows != 1) {
                status.setRollbackOnly();
                return R.fail(ms.getMessage("db.update.recharge.failure", null, locale));
            }
            return R.success(ms.getMessage("db.update.recharge.success", null, locale));
        });

        return result;
    }

    /**
     * 修改卡状态
     * 只能修改状态为 normal|loss，canceled状态只能通过注销接口来修改
     * {@link cn.qkmango.ccms.mvc.controller.AccountController#canceled(CanceledDto)}
     * {@link cn.qkmango.ccms.mvc.service.impl.AccountServiceImpl#canceled(CanceledDto)}
     */
    @Override
    public boolean state(Integer account, CardState state, Integer version) {
        Card card = cardDao.getByAccount(account);

        // 判断状态
        // 1. 如果已注销则不能修改
        // 2. 如果通过此接口修改状态为注销，也不行，注销状态只能通过注销接口来操作，普通的修改状态不能设置为注销
        if (card.getState() == CardState.canceled || state == CardState.canceled) {
            return false;
        }

        Boolean result = tx.execute(status -> {
            int affectedRows = cardDao.state(account, state, version);
            if (affectedRows == 1) {
                return true;
            }
            status.setRollbackOnly();
            return false;
        });

        return Boolean.TRUE.equals(result);
    }

    /**
     * 根据账户ID查询卡信息
     */
    @Override
    public Card recordByAccount(Integer account) {
        return cardDao.getByAccount(account);
    }

}
