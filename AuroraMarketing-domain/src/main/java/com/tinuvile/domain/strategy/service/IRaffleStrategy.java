package com.tinuvile.domain.strategy.service;


import com.tinuvile.domain.strategy.model.entity.RaffleAwardEntity;
import com.tinuvile.domain.strategy.model.entity.RaffleFactorEntity;

/**
 * @author Tinuvile
 * @description 抽奖策略接口
 * @since 2025/11/25
 */
public interface IRaffleStrategy {

    /**
     * 执行抽奖
     *
     * @param raffleFactorEntity 抽奖因子实体
     * @return 抽奖结果实体
     */
    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity);

}
