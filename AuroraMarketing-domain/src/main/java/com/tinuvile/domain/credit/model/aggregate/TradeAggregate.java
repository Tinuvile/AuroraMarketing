package com.tinuvile.domain.credit.model.aggregate;


import com.tinuvile.domain.award.model.valobj.TaskStateVO;
import com.tinuvile.domain.credit.event.CreditAdjustSuccessMessageEvent;
import com.tinuvile.domain.credit.model.entity.CreditAccountEntity;
import com.tinuvile.domain.credit.model.entity.CreditOrderEntity;
import com.tinuvile.domain.credit.model.entity.TaskEntity;
import com.tinuvile.domain.credit.model.valobj.TradeNameVO;
import com.tinuvile.domain.credit.model.valobj.TradeTypeVO;
import com.tinuvile.types.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

/**
 * @author Tinuvile
 * @description 交易聚合对象
 * @since 2026/1/27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeAggregate {

    /** 用户ID */
    private String userId;

    /** 用户积分账户实体 */
    private CreditAccountEntity creditAccountEntity;

    /** 用户积分订单实体 */
    private CreditOrderEntity creditOrderEntity;

    /** 任务实体 - 补偿MQ消息 */
    private TaskEntity taskEntity;

    public static CreditAccountEntity createCreditAccountEntity(String userId, BigDecimal adjustAmount) {
        return CreditAccountEntity.builder().userId(userId).adjustAmount(adjustAmount).build();
    }

    public static CreditOrderEntity createCreditOrderEntity(String userId, TradeNameVO tradeName, TradeTypeVO tradeType, BigDecimal tradeAmount, String outBusinessNo
    ) {
        return CreditOrderEntity.builder()
                .userId(userId)
                .orderId(RandomStringUtils.randomNumeric(12))
                .tradeName(tradeName)
                .tradeType(tradeType)
                .tradeAmount(tradeAmount)
                .outBusinessNo(outBusinessNo)
                .build();
    }

    public static TaskEntity createTaskEntity(String userId, String topic, String messageId, BaseEvent.EventMessage<CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage> message) {
        return TaskEntity.builder()
                .userId(userId)
                .topic(topic)
                .messageId(messageId)
                .message(message)
                .state(TaskStateVO.create)
                .build();
    }

}
