package com.tinuvile.domain.strategy.service.rule.tree.factory.engine;


import com.tinuvile.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author Tinuvile
 * @description 规则树组合接口
 * @since 2025/11/30
 */
public interface IDecisionTreeEngine {

    DefaultTreeFactory.StrategyAwardData process(String userId, Long strategyId, Integer awardId);

}
