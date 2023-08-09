package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.cache.qrcode.QrCodeCache;
import cn.qkmango.ccms.common.exception.database.QueryException;
import cn.qkmango.ccms.common.exception.database.UpdateException;
import cn.qkmango.ccms.common.util.SnowFlake;
import cn.qkmango.ccms.domain.bind.CardState;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel1;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel2;
import cn.qkmango.ccms.domain.bind.trade.TradeLevel3;
import cn.qkmango.ccms.domain.bind.trade.TradeState;
import cn.qkmango.ccms.domain.bo.AccountPayQrcode;
import cn.qkmango.ccms.domain.dto.QrCodeConsume;
import cn.qkmango.ccms.domain.entity.Card;
import cn.qkmango.ccms.domain.entity.Trade;
import cn.qkmango.ccms.mvc.dao.CardDao;
import cn.qkmango.ccms.mvc.dao.TradeDao;
import cn.qkmango.ccms.mvc.service.PayService;
import jakarta.annotation.Resource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * 描述
 * <p></p>
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-07 19:45
 */
@Service
public class PayServiceImpl implements PayService {

    @Resource
    private ReloadableResourceBundleMessageSource ms;

    @Resource
    private QrCodeCache qrCodeCache;

    @Resource
    private TradeDao tradeDao;

    @Resource
    private CardDao cardDao;

    @Resource
    private SnowFlake snowFlake;


    /**
     * 创建支付二维码，并缓存到redis
     * key为 pay:qrcode:accountId
     * value 为 UUID
     *
     * @return 二维码
     */
    @Override
    public AccountPayQrcode createQrCode(Integer account) {
        String code = UUID.randomUUID().toString();
        AccountPayQrcode qrcode = new AccountPayQrcode()
                .setAccount(account)
                .setCode(code);
        qrCodeCache.set(account, code);
        return qrcode;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void consumeByQrCode(QrCodeConsume consume) throws QueryException, UpdateException {
        //账户
        Integer account = consume.getAccount();
        //提交的二维码
        String postCode = consume.getCode();

        //1. 检查二维码是否与缓存中的二维码一致
        boolean checked = qrCodeCache.checkOkDelete(account, postCode);
        if (!checked) {
            throw new QueryException(ms.getMessage("response.cache.not.exist@qrcode", null, LocaleContextHolder.getLocale()));
        }

        //2. 检查卡
        Card card = cardDao.getRecordByAccount(account);
        //卡不存在
        if (card == null) {
            throw new QueryException(ms.getMessage("db.card.failure@notExist", null, LocaleContextHolder.getLocale()));
        }
        //卡状态
        if (card.getState() != CardState.normal) {
            throw new QueryException(ms.getMessage("db.card.failure@state", null, LocaleContextHolder.getLocale()));
        }
        //卡余额
        if (card.getBalance() < consume.getAmount()) {
            throw new QueryException(ms.getMessage("db.card.balance.insufficient", null, LocaleContextHolder.getLocale()));
        }

        int affectedRows = 0;

        // 3. 扣除卡余额
        affectedRows = cardDao.updateBalance(card.getId(), consume.getAmount(), card.getVersion());
        if (affectedRows != 1) {
            throw new UpdateException(ms.getMessage("db.card.update.failure@balance", null, LocaleContextHolder.getLocale()));
        }

        Trade trade = new Trade()
                .setId(snowFlake.nextId())
                .setAccount(account)
                .setLevel1(TradeLevel1.out)
                .setLevel2(TradeLevel2.qr)
                .setLevel3(TradeLevel3.consume)
                .setState(TradeState.success)
                .setAmount(consume.getAmount())
                .setCreateTime(System.currentTimeMillis())
                .setVersion(0);


        // 4. 添加交易记录
        affectedRows = tradeDao.insert(trade);
        if (affectedRows != 1) {
            throw new UpdateException(ms.getMessage("db.trade.insert.failure", null, LocaleContextHolder.getLocale()));
        }
    }
}
