package com.tinuvile.domain.strategy.model.entity;


import com.tinuvile.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import lombok.*;

/**
 * @author Tinuvile
 * @description 规则执行结果封装类
 * @since 2025/11/25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleActionEntity<T extends RuleActionEntity.RaffleEntity> {

    private String code = RuleLogicCheckTypeVO.ALLOW.getCode();
    private String info = RuleLogicCheckTypeVO.ALLOW.getInfo();
    private String ruleModel;
    private T data;

    static public class RaffleEntity {}

    /**
     * 抽奖前置规则
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static public class RaffleBeforeEntity extends RaffleEntity {

        /** 策略ID */
        private Long strategyId;

        /** 规则权重值Key */
        private String ruleWeightValueKey;

        /** 奖品ID */
        private Integer awardId;

    }

    static public class RaffleCenterEntity extends RaffleEntity {}

    static public class RaffleAfterEntity extends RaffleEntity {}

}
