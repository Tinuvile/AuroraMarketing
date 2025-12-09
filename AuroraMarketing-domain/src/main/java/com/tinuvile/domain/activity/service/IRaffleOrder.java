package com.tinuvile.domain.activity.service;


import com.tinuvile.domain.activity.model.entity.ActivityOrderEntity;
import com.tinuvile.domain.activity.model.entity.ActivityShopCartEntity;

/**
 * @author Tinuvile
 * @description 抽奖活动订单接口
 * @since 2025/12/9
 */
public interface IRaffleOrder {

    /**
     * 以sku创建抽奖活动订单，获得抽奖资格
     *
     * @param activityShopCartEntity 抽奖活动sku实体
     * @return 抽奖活动参与记录实体
     */
    ActivityOrderEntity createRaffleActivityOrder(ActivityShopCartEntity activityShopCartEntity);

}
