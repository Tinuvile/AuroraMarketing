package com.tinuvile.infrastructure.presistent.po;


import lombok.Data;

import java.util.Date;

/**
 * @author Tinuvile
 * @description 策略规则
 * @since 2025/11/23
 */
@Data
public class StrategyRule {

    /** 自增ID */
    private Long id;

    /** 策略ID */
    private Long strategyId;

    /** 奖品ID */
    private Integer awardId;

    /** 抽象规则类型；1-策略规则、2-奖品规则 */
    private Integer ruleType;

    /**
     * 抽奖规则类型
     * <p>
     * 可选值：
     * <ul>
     *   <li>rule_random - 随机值计算</li>
     *   <li>rule_lock - 抽奖几次后解锁</li>
     *   <li>rule_luck_award - 幸运奖（兜底）</li>
     *   <li>rule_weight - 抽满次数自动送</li>
     *   <li>rule_blacklist - 黑名单</li>
     * </ul>
     * </p>
     */
    private String ruleModel;

    /** 抽奖规则比值 */
    private String ruleValue;

    /** 抽奖规则描述 */
    private String ruleDesc;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

}
