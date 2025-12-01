package com.tinuvile.domain.strategy.model.valobj;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description 抽奖策略规则值对象
 * @since 2025/11/26
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardRuleModelVO {

    private String ruleModels;

}
