package com.tinuvile.domain.strategy.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Tinuvile
 * @description 策略结果实体
 * @since 2025/11/23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardEntity {

    /** 策略ID */
    private Long strategyId;

    /** 奖品ID */
    private Integer awardId;

    /** 奖品名称 */
    private String awardTitle;

    /** 奖品副标题 */
    private String awardSubtitle;

    /** 奖品库存 */
    private Integer awardCount;

    /** 奖品库存剩余 */
    private Integer awardCountSurplus;

    /** 奖品中奖概率 */
    private BigDecimal awardRate;

    /** 排序编号 */
    private Integer sort;

    /** 规则模型，rule配置的模型同步到此表 */
    private String ruleModels;

}
