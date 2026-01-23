package com.tinuvile.domain.award.service.distribute;


import com.tinuvile.domain.award.model.entity.DistributeAwardEntity;

/**
 * @author Tinuvile
 * @description 分发奖励接口
 * @since 2026/1/23
 */
public interface IDistributeAward {

    void giveOutPrizes(DistributeAwardEntity distributeAwardEntity);

}
