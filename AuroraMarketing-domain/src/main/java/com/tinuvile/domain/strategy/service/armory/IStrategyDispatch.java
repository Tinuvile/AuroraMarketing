package com.tinuvile.domain.strategy.service.armory;


/**
 * @author Tinuvile
 * @description 策略抽奖调度器
 * @since 2025/11/24
 */
public interface IStrategyDispatch {

    /**
     * 获取策略装配的随机结果
     * @param strategyId 策略ID
     * @return 抽奖结果
     */
    Integer getRandomAwardId(Long strategyId);

}
