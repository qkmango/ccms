package cn.qkmango.ccms.middleware.mq.trade.pay.alipay.timeout;

import cn.qkmango.ccms.domain.bind.trade.TradeState;
import cn.qkmango.ccms.domain.entity.Trade;
import cn.qkmango.ccms.mvc.dao.TradeDao;
import cn.qkmango.ccms.mvc.service.AlipayService;
import cn.qkmango.ccms.pay.AlipayTradeStatus;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
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
    @Resource
    private AlipayService alipayService;
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
            // 参数无效,重新查询
            case "ACQ.INVALID_PARAMETER" -> {
                throw new RuntimeException("参数无效");
            }
            // 交易状态不对，主动查询交易状态
            case "ACQ.TRADE_STATUS_ERROR",              // 交易状态不合法,只有等待买家付款状态下才能发起交易关闭(查询交易状态)
                    "ACQ.REASON_TRADE_STATUS_INVALID",  // 交易状态异常,非待支付状态下不支持关单操作
                    "ACQ.REASON_ILLEGAL_STATUS"         // 交易状态异常,非待支付状态下不支持关单操作
                    -> {
                AlipayTradeQueryResponse tradeQueryResponse = this.alipayTradeQuery(tradeId);
                if (tradeQueryResponse == null) {
                    throw new RuntimeException("支付宝交易查询接口调用失败");
                }

                // Trade trade = this.tradeDao.getById(tradeId);
                // 不在支付中则不需要关单
                if (trade.getState() != TradeState.processing) {
                    return;
                }

                if (!response.isSuccess()) {
                    throw new RuntimeException("失败:支付宝交易查询接口调用失败");
                }

                AlipayTradeStatus status = AlipayTradeStatus.valueOf(tradeQueryResponse.getTradeStatus());
                switch (status) {
                    // 如果交易状态为完成或者结束，则需要修改数据库的交易状态和余额
                    case TRADE_SUCCESS, TRADE_FINISHED -> {
                        boolean result = this.alipayService.updateTradeBalance(trade.getAccount(), trade.getAmount(), trade.getId(), response.getTradeNo(), trade.getVersion());
                        if (!result) {
                            throw new RuntimeException("失败:修改交易状态或余额失败");
                        }
                    }

                    // 未付款交易超时关闭，或支付完成后全额退款
                    case TRADE_CLOSED -> {
                        boolean result = this.closeInTrade(tradeId, trade.getVersion());
                        if (!result) {
                            throw new RuntimeException("未付款支付宝交易超时关闭，或支付完成后全额退款");
                        }
                    }
                    // 不可能到达此位置，因为如果为等待支付，则上面调用支付宝接口直接就能关单了
                    case WAIT_BUYER_PAY -> {
                    }

                }
            }
        }
    }

    /**
     * 关闭系统交易
     */
    private boolean closeInTrade(Long tradeId, Integer version) {
        Boolean result = tx.execute(status -> {
            int affectedRows;
            affectedRows = tradeDao.updateState(tradeId, TradeState.close, version);
            if (affectedRows != 1) {
                status.setRollbackOnly();
                return false;
            }
            return true;
        });
        return Boolean.TRUE.equals(result);
    }


    /**
     * 主动查询支付宝交易状态
     */
    private AlipayTradeQueryResponse alipayTradeQuery(Long tradeId) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(tradeId.toString());
        request.setBizModel(model);
        try {
            return client.execute(request);
        } catch (AlipayApiException e) {
            return null;
        }
    }
}
