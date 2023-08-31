package cn.qkmango.ccms.middleware.mq.trade.pay.alipay.timeout;

import cn.qkmango.ccms.domain.bind.trade.TradeState;
import cn.qkmango.ccms.domain.entity.Trade;
import cn.qkmango.ccms.mvc.dao.TradeDao;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import jakarta.annotation.Resource;
import org.apache.log4j.Logger;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import static cn.qkmango.ccms.middleware.mq.trade.pay.alipay.timeout.TradePayAlipayTimeoutMQConfig.GROUP;
import static cn.qkmango.ccms.middleware.mq.trade.pay.alipay.timeout.TradePayAlipayTimeoutMQConfig.TOPIC;

/**
 * 交易支付订单超时处理监听（支付宝）
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-08-24 19:44
 */
@Component
@RocketMQMessageListener(consumerGroup = GROUP, topic = TOPIC)
public class TradePayAlipayTimeoutMQListener implements RocketMQListener<Long> {

    @Resource(name = "alipayClient")
    private AlipayClient client;
    @Resource
    private TradeDao tradeDao;
    @Resource
    private TransactionTemplate tx;
    private final Logger logger = Logger.getLogger(getClass());

    /**
     * 订单超时后处理
     */
    @Override
    public void onMessage(Long tradeId) {
        logger.info("超时关闭订单: " + tradeId);
        Trade trade = tradeDao.getById(tradeId);

        // 1. 判断交易状态：如果超时了，交易不在支付中说明，无需修改交易状态（不需要关单）
        if (trade.getState() != TradeState.processing) {
            return;
        }

        // 如果超时了，交易还在支付中，那么将状态修改为超时关闭（关单）
        // 只有用户登陆了支付宝支付页面，支付宝的交易才会创建
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", tradeId);
        request.setBizContent(bizContent.toString());

        // 2. 发起关单请求
        AlipayTradeCloseResponse response = null;
        try {
            response = client.execute(request);
        } catch (AlipayApiException e) {
            logger.warn(e.getMessage());
        }
        if (response == null) {
            return;
        }

        // 3A关单成功
        if (response.isSuccess()) {
            // 此时如果其他线程修改交易，更新了版本号导系统交易关单失败，
            // 此时支付宝交易关单无法支付，但系统交易还在待支付
            this.closeInTrade(tradeId, trade.getVersion());
            return;
        }
        // 3B关单失败
        switch (response.getSubCode()) {
            // 系统异常，重新发起请求
            case "ACQ.SYSTEM_ERROR" -> throw new RuntimeException("支付宝系统异常");
            // 交易不存在(用户未登录支付宝)，关闭交易
            // 此时如果其他线程修改交易，更新了版本号导系统交易关单失败，
            // 此时支付宝交易也超时无法支付，但系统交易还在待支付
            case "ACQ.TRADE_NOT_EXIST" -> {
                this.closeInTrade(tradeId, trade.getVersion());
            }
            case "ACQ.TRADE_STATUS_ERROR",              // 交易状态不合法,只有等待买家付款状态下才能发起交易关闭
                    "ACQ.INVALID_PARAMETER",            // 参数无效
                    "ACQ.REASON_TRADE_STATUS_INVALID",  // 交易状态异常,非待支付状态下不支持关单操作
                    "ACQ.REASON_ILLEGAL_STATUS"         // 交易状态异常,非待支付状态下不支持关单操作
                    -> throw new RuntimeException("交易状态异常,非待支付状态下不支持关单操作");
        }
    }

    /**
     * 关闭系统交易
     */
    public void closeInTrade(Long tradeId, Integer version) {
        tx.execute(status -> {
            int affectedRows;
            affectedRows = tradeDao.updateState(tradeId, TradeState.close, version);
            if (affectedRows != 1) {
                status.setRollbackOnly();
                return null;
            }
            return null;
        });
    }
}
