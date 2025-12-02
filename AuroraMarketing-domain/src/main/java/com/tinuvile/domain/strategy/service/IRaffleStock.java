package com.tinuvile.domain.strategy.service;


import com.tinuvile.domain.strategy.model.valobj.StrategyAwardStockKeyVO;

/**
 * @author Tinuvile
 * @description 抽奖库存相关服务，获取库存消耗队列
 * @since 2025/12/2
 */
public interface IRaffleStock {

    /**
     * 获取奖品库存消耗队列
     *
     * @return 奖品库存Key信息
     * @throws InterruptedException 线程中断异常
     */
    StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException;

    /**
     * 更新奖品库存
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     */
    void updateStrategyAwardStock(Long strategyId, Integer awardId);

}
