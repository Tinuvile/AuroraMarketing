package com.tinuvile.domain.activity.service.armory;


import java.util.Date;

/**
 * @author Tinuvile
 * @description 活动调度【扣减库存】
 * @since 2025/12/23
 */
public interface IActivityDispatch {

    /**
     * 根据策略ID和奖品ID，扣减奖品缓存库存
     *
     * @param sku 互动SKU
     * @param endDateTime 活动结束时间
     * @return 是否扣减成功
     */
    boolean subtractionActivitySkuStock(Long sku, Date endDateTime);

}
