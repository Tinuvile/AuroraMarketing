package com.tinuvile.domain.strategy.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description 规则物料实体类 - 用于过滤规则的必要参数信息
 * @since 2025/11/25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleMatterEntity {

    /** 用户ID */
    private String userId;

    /** 策略ID */
    private Long strategyId;

    /** 奖品ID - 如果规则类型为策略，则不需要 */
    private Integer awardId;

    /** 抽奖规则类型 - rule_random 随机值计算、rule_lock 次数解锁、rule_luck_award 兜底奖品 */
    private String ruleModel;

}
