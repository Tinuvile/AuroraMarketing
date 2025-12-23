package com.tinuvile.domain.activity.service;


import com.tinuvile.domain.activity.model.valobj.ActivitySkuStockKeyVO;

/**
 * @author Tinuvile
 * @description 活动sku库存处理接口
 * @since 2025/12/23
 */
public interface ISkuStock {

    /**
     * 获取活动sku库存消耗队列
     *
     * @return 奖品库存key信息
     * @throws InterruptedException 线程中断异常
     */
    ActivitySkuStockKeyVO takeQueueValue() throws InterruptedException;

    /**
     * 清空活动sku库存消耗队列
     */
    void clearQueueValue();

    /**
     * 延迟队列+任务趋势 更新活动sku库存
     *
     * @param sku 商品sku
     */
    void updateActivitySkuStock(Long sku);

    /**
     * 缓存库存已消耗完，清空数据库活动sku库存
     *
     * @param sku 商品sku
     */
    void clearActivitySkuStock(Long sku);

}
