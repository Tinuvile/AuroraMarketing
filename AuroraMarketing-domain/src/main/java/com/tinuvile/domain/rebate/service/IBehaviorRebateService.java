package com.tinuvile.domain.rebate.service;


import com.tinuvile.domain.rebate.model.entity.BehaviorEntity;
import com.tinuvile.domain.rebate.model.entity.BehaviorRebateOrderEntity;

import java.util.List;

/**
 * @author Tinuvile
 * @description 行为返利服务接口
 * @since 2026/1/16
 */
public interface IBehaviorRebateService {

    /**
     * 创建行为动作的入账订单
     *
     * @param behaviorEntity 行为实体对象
     * @return 订单ID
     */
    List<String> createOrder(BehaviorEntity behaviorEntity);

    /**
     * 根据外部单号查询订单
     *
     * @param userId       用户ID
     * @param outBusinessNo 外部业务ID（签到则是日期字符串，支付则是外部的业务ID）
     * @return 返利订单实体列表
     */
    List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo);

}
