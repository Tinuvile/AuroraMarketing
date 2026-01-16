package com.tinuvile.domain.rebate.repository;


import com.tinuvile.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import com.tinuvile.domain.rebate.model.valobj.BehaviorTypeVO;
import com.tinuvile.domain.rebate.model.valobj.DailyBehaviorRebateVO;

import java.util.List;

/**
 * @author Tinuvile
 * @description 行为返利服务仓储接口
 * @since 2026/1/16
 */
public interface IBehaviorRebateRepository {

    List<DailyBehaviorRebateVO> queryDailyBehaviorRebateConfig(BehaviorTypeVO behaviorTypeVO);

    void saveUserRebateRecord(String userId, List<BehaviorRebateAggregate> behaviorRebateAggregates);

}
