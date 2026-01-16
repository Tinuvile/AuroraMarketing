package com.tinuvile.domain.rebate.service;


import com.tinuvile.domain.rebate.model.entity.BehaviorEntity;

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

}
