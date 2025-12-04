package com.tinuvile.domain.strategy.service;


import com.tinuvile.domain.strategy.model.entity.StrategyAwardEntity;

import java.util.List;

/**
 * @author Tinuvile
 * @description 策略奖品接口
 * @since 2025/12/4
 */
public interface IRaffleAward {

    /**
     * 查询策略奖品列表
     *
     * @param strategyId 策略ID
     * @return 策略奖品列表
     */
    List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId);

}
