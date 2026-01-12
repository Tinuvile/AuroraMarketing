package com.tinuvile.domain.activity.service;


import com.tinuvile.domain.activity.model.entity.PartakeRaffleActivityEntity;
import com.tinuvile.domain.activity.model.entity.UserRaffleOrderEntity;

/**
 * @author Tinuvile
 * @description 抽奖活动参与服务
 * @since 2026/1/10
 */
public interface IRaffleActivityPartakeService {

    /**
     * 创建抽奖订单：用户参与抽奖活动，扣减活动账户库存，生成抽奖订单。如存在未被使用的订单，直接返回该订单。
     *
     * @param partakeRaffleActivityEntity 参与抽奖活动实体对象
     * @return 抽奖订单实体对象
     */
    UserRaffleOrderEntity createOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity);

    /**
     * 创建抽奖订单：用户参与抽奖活动，扣减活动账户库存，生成抽奖订单。如存在未被使用的订单，直接返回该订单。
     *
     * @param userId    用户ID
     * @param activityId 活动ID
     * @return 抽奖订单实体对象
     */
    UserRaffleOrderEntity createOrder(String userId, Long activityId);

}
