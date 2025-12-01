package com.tinuvile.domain.strategy.service.rule.chain;


import com.tinuvile.domain.strategy.service.rule.chain.factory.DefaultChainFactory;

/**
 * @author Tinuvile
 * @description 抽奖策略规则责任链接口
 * @since 2025/11/27
 */
public interface ILogicChain extends ILogicChainArmory, Cloneable {

    /**
     * 责任链接口
     *
     * @param userId 用户ID
     * @param strategyId 抽奖策略ID
     * @return 奖品对象
     */
    DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId);

}
