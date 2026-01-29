package com.tinuvile.domain.activity.service;


import com.tinuvile.domain.activity.model.entity.SkuProductEntity;

import java.util.List;

/**
 * @author Tinuvile
 * @description sku商品服务接口
 * @since 2026/1/29
 */
public interface IRaffleActivitySkuProductService {

    /**
     * 查询当前活动ID下创建的sku商品。【sku可以兑换活动抽奖次数】
     *
     * @param activityId 活动id
     * @return sku商品列表
     */
    List<SkuProductEntity> querySkuProductEntityListByActivityId(Long activityId);

}
