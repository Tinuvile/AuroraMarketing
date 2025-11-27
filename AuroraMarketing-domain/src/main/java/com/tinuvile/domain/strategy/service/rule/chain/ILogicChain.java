package com.tinuvile.domain.strategy.service.rule.chain;


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
     * @return 奖品ID
     */
    Integer logic(String userId, Long strategyId);

}
