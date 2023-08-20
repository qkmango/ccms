package cn.qkmango.ccms.mvc.service.impl;

import cn.qkmango.ccms.common.cache.qrcode.QrCodeCache;
import cn.qkmango.ccms.common.map.R;
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
import cn.qkmango.ccms.mvc.service.PaySystemService;
import jakarta.annotation.Resource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Locale;
import java.util.UUID;

/**
 * 支付
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-20 16:27
 */
@Service
public class PaySystemServiceImpl implements PaySystemService {

    @Resource
    private TradeDao tradeDao;

    @Resource
    private CardDao cardDao;

    @Resource
    private TransactionTemplate tx;

    @Resource
    private QrCodeCache qrCodeCache;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private ReloadableResourceBundleMessageSource ms;

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

    /**
     * 二维码支付消费
     *
     * @param consume 二维码消费信息
     */
    @Override
    public R consumeByQrCode(QrCodeConsume consume) {
        Locale locale = LocaleContextHolder.getLocale();
        // 账户
        Integer account = consume.getAccount();
        // 提交的二维码
        String postQrcode = consume.getCode();

        // 1. 检查二维码是否与缓存中的二维码一致
        boolean checked = qrCodeCache.checkOkDelete(account, postQrcode);
        if (!checked) {
            return R.fail(ms.getMessage("response.cache.not.exist@qrcode", null, locale));
        }

        // 2. 检查卡
        Card card = cardDao.getRecordByAccount(account);
        // 卡不存在
        if (card == null) {
            return R.fail(ms.getMessage("db.card.failure@notExist", null, locale));
        }
        // 卡状态
        if (card.getState() != CardState.normal) {
            return R.fail(ms.getMessage("db.card.failure@state", null, locale));
        }
        // 卡余额
        if (card.getBalance() < consume.getAmount()) {
            return R.fail(ms.getMessage("db.card.balance.insufficient", null, locale));
        }


        // 交易记录
        Trade trade = new Trade()
                .setId(snowFlake.nextId())
                .setAccount(account)
                .setLevel1(TradeLevel1.out)
                .setLevel2(TradeLevel2.qr)
                .setLevel3(TradeLevel3.consume)
                .setState(TradeState.success)
                .setAmount(consume.getAmount())
                .setCreator(consume.getCreator())
                .setCreateTime(System.currentTimeMillis())
                .setVersion(0);

        // 执行事务
        R<Object> result = tx.execute(status -> {
            int affectedRows;
            // 3. 扣除卡余额
            affectedRows = cardDao.consume(card.getId(), consume.getAmount(), card.getVersion());
            if (affectedRows != 1) {
                status.setRollbackOnly();
                return R.fail(ms.getMessage("db.card.update.failure@balance", null, locale));
            }

            // 4. 添加交易记录
            affectedRows = tradeDao.insert(trade);
            if (affectedRows != 1) {
                status.setRollbackOnly();
                return R.fail(ms.getMessage("db.card.update.failure@balance", null, locale));
            }

            // 成功
            return R.success(ms.getMessage("db.trade.insert.success@pay", null, locale));
        });

        return result;
    }
}
