package com.tinuvile.domain.strategy.service.armory;


/**
 * @author Tinuvile
 * @description 策略抽奖调度器
 * @since 2025/11/24
 */
public interface IStrategyDispatch {

    /**
     * 获取策略装配的随机结果
     *
     * @param strategyId 策略ID
     * @return 抽奖结果
     */
    Integer getRandomAwardId(Long strategyId);

    /**
     * 获取策略装配的随机结果
     *
     * @param strategyId 策略ID
     * @param ruleWeightValue 抽奖规则比值
     * @return 抽奖结果
     */
    Integer getRandomAwardId(Long strategyId, String ruleWeightValue);

    /**
     * 获取策略装配的随机结果
     *
     * @param key = strategyId + _ + ruleWeightValue
     * @return 抽奖结果
     */
    Integer getRandomAwardId(String key);

    /**
     * 根据策略ID和奖品ID，扣减奖品缓存库存
     *
     * @param strategyId 策略ID
     * @param awardId 奖品ID
     * @return 是否成功
     */
    Boolean subtractionAwardStock(Long strategyId, Integer awardId);

}
