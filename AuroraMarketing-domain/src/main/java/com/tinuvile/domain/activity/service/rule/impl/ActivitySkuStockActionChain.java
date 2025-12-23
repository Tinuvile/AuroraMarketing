package com.tinuvile.domain.activity.service.rule.impl;


import com.tinuvile.domain.activity.model.entity.ActivityCountEntity;
import com.tinuvile.domain.activity.model.entity.ActivityEntity;
import com.tinuvile.domain.activity.model.entity.ActivitySkuEntity;
import com.tinuvile.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import com.tinuvile.domain.activity.repository.IActivityRepository;
import com.tinuvile.domain.activity.service.armory.IActivityDispatch;
import com.tinuvile.domain.activity.service.rule.AbstractActionChain;
import com.tinuvile.types.enums.ResponseCode;
import com.tinuvile.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Tinuvile
 * @description 商品库存规则节点
 * @since 2025/12/22
 */
@Slf4j
@Component("activity_sku_stock_action")
public class ActivitySkuStockActionChain extends AbstractActionChain {

    @Resource
    private IActivityDispatch activityDispatch;
    @Resource
    private IActivityRepository activityRepository;

    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {

        log.info("活动责任链 - 商品库存处理【校验&扣减】开始。sku:{}, activityId:{}", activitySkuEntity.getSku(), activitySkuEntity.getActivityId());

        // 扣减库存
        boolean status = activityDispatch.subtractionActivitySkuStock(activitySkuEntity.getSku(), activityEntity.getEndDateTime());
        if (status) {
            log.info("活动责任链 - 商品库存处理【校验&扣减】成功。sku:{}, activityId:{}", activitySkuEntity.getSku(), activitySkuEntity.getActivityId());

            // 写入延迟队列
            activityRepository.activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO.builder()
                    .sku(activitySkuEntity.getSku())
                    .activityId(activitySkuEntity.getActivityId())
                    .build()
            );

            return true;
        }

        throw new AppException(ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getCode(), ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getInfo());
    }

}
