package com.tinuvile.domain.activity.repository;


import com.tinuvile.domain.activity.model.aggregate.CreateOrderAggregate;
import com.tinuvile.domain.activity.model.entity.ActivityCountEntity;
import com.tinuvile.domain.activity.model.entity.ActivityEntity;
import com.tinuvile.domain.activity.model.entity.ActivitySkuEntity;

/**
 * @author Tinuvile
 * @description 活动仓储接口
 * @since 2025/12/9
 */
public interface IActivityRepository {

    ActivitySkuEntity queryActivitySku(Long sku);

    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);

    void doSaveOrder(CreateOrderAggregate createOrderAggregate);

}
