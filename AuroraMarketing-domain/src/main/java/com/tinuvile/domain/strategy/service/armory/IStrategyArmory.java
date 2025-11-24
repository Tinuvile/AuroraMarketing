package com.tinuvile.domain.strategy.service.armory;


/**
 * @author Tinuvile
 * @description 策略装配库（兵工厂），负责初始化策略计算
 * @since 2025/11/23
 */
public interface IStrategyArmory {

    /**
     * 装配抽奖策略配置
     * 【活动审核通过后调用】
     * @param strategyId 策略ID
     * @return 是否装配成功
     */
    boolean assembleLotteryStrategy(Long strategyId);

}
