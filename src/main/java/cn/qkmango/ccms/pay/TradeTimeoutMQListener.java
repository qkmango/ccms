package cn.qkmango.ccms.pay;

import cn.qkmango.ccms.common.exception.GlobalExceptionHandler;
import cn.qkmango.ccms.domain.bind.trade.TradeState;
import cn.qkmango.ccms.domain.entity.Trade;
import cn.qkmango.ccms.mvc.dao.TradeDao;
import cn.qkmango.ccms.mvc.dao.TradeTimeoutDao;
import jakarta.annotation.Resource;
import org.apache.log4j.Logger;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 处理支付宝支付订单超时
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-24 19:44
 */
@Service
@RocketMQMessageListener(consumerGroup = "ccms", topic = "pay-trade-alipay-timeout")
public class TradeTimeoutMQListener implements RocketMQListener<Long> {

    @Resource
    private TradeDao tradeDao;

    @Resource
    private TradeTimeoutDao tradeTimeoutDao;

    @Resource
    private TransactionTemplate tx;

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    /**
     * 订单超时后处理
     */
    @Override
    public void onMessage(Long tradeId) {
        Trade trade = tradeDao.getRecordById(tradeId);
        // 如果超时了，交易还在支付中，那么将状态修改为超时关闭
        if (trade.getState() == TradeState.processing) {
            tx.execute(status -> {
                int affectedRows;
                affectedRows = tradeDao.updateState(tradeId, TradeState.close, trade.getVersion());
                if (affectedRows != 1) {
                    status.setRollbackOnly();
                    return null;
                }
                affectedRows = tradeTimeoutDao.deleteByTradeId(tradeId);
                if (affectedRows != 1) {
                    status.setRollbackOnly();
                    return null;
                }
                return null;
            });
            logger.info("超时关闭订单: " + tradeId);
        }
    }
}
