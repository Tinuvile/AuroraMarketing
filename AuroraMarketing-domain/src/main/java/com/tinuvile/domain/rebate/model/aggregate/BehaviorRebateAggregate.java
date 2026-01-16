package com.tinuvile.domain.rebate.model.aggregate;


import com.tinuvile.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import com.tinuvile.domain.rebate.model.entity.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description 行为返利聚合对象
 * @since 2026/1/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BehaviorRebateAggregate {

    private String userId;

    private BehaviorRebateOrderEntity behaviorRebateOrderEntity;

    private TaskEntity taskEntity;

}
