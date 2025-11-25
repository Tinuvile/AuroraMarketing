package com.tinuvile.domain.strategy.service.rule;


import com.tinuvile.domain.strategy.model.entity.RuleActionEntity;
import com.tinuvile.domain.strategy.model.entity.RuleMatterEntity;

/**
 * @author Tinuvile
 * @description 抽奖规则过滤接口
 * @since 2025/11/25
 */
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {

    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);

}
