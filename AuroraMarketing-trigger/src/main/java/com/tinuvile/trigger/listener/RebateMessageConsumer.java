package com.tinuvile.trigger.listener;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tinuvile.domain.activity.model.entity.SkuRechargeEntity;
import com.tinuvile.domain.activity.model.valobj.OrderTradeTypeVO;
import com.tinuvile.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.tinuvile.domain.credit.model.entity.TradeEntity;
import com.tinuvile.domain.credit.model.valobj.TradeNameVO;
import com.tinuvile.domain.credit.model.valobj.TradeTypeVO;
import com.tinuvile.domain.credit.service.ICreditAdjustService;
import com.tinuvile.domain.rebate.event.SendRebateMessageEvent;
import com.tinuvile.types.enums.ResponseCode;
import com.tinuvile.types.event.BaseEvent;
import com.tinuvile.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author Tinuvile
 * @description 监听行为返利消息
 * @since 2026/1/20
 */
@Slf4j
@Component
public class RebateMessageConsumer {

    @Value("${spring.rabbitmq.topic.send_rebate}")
    private String topic;

    @Resource
    private IRaffleActivityAccountQuotaService raffleActivityAccountQuotaService;
    @Resource
    private ICreditAdjustService creditAdjustService;

    @RabbitListener(queuesToDeclare = @Queue("${spring.rabbitmq.topic.send_rebate}"))
    public void listener(String message) {
        try {
            log.info("监听用户行为返利消息 topic:{} message:{}", topic, message);
            // 转换消息
            BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage> eventMessage = JSON.parseObject(
                    message,
                    new TypeReference<BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage>>() {
                    }.getType()
            );
            SendRebateMessageEvent.RebateMessage rebateMessage = eventMessage.getData();

            // 入账奖励
            switch (rebateMessage.getRebateType()) {
                case "sku":
                    SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
                    skuRechargeEntity.setUserId(rebateMessage.getUserId());
                    skuRechargeEntity.setSku(Long.valueOf(rebateMessage.getRebateConfig()));
                    skuRechargeEntity.setOutBusinessNo(rebateMessage.getBizId());
                    skuRechargeEntity.setOrderTradeType(OrderTradeTypeVO.rebate_no_pay_trade);
                    raffleActivityAccountQuotaService.createOrder(skuRechargeEntity);
                    break;
                case "credit":
                    TradeEntity tradeEntity = new TradeEntity();
                    tradeEntity.setUserId(rebateMessage.getUserId());
                    tradeEntity.setTradeName(TradeNameVO.REBATE);
                    tradeEntity.setTradeType(TradeTypeVO.FORWARD);
                    tradeEntity.setAmount(new BigDecimal(rebateMessage.getRebateConfig()));
                    tradeEntity.setOutBusinessNo(rebateMessage.getBizId());
                    creditAdjustService.createOrder(tradeEntity);
                    break;
            }
        } catch (AppException e) {
            if (ResponseCode.INDEX_DUPLICATE.getCode().equals(e.getCode())) {
                log.warn("监听用户行为返利消息，消费重复 topic:{} message:{}", topic, message, e);
                return;
            }
            throw e;
        } catch (Exception e) {
            log.error("监听用户行为返利消息，消费失败 topic:{} message:{}", topic, message, e);
            throw e;
        }
    }
}
