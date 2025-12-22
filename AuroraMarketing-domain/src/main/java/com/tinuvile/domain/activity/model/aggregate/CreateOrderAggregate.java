package com.tinuvile.domain.activity.model.aggregate;


import com.tinuvile.domain.activity.model.entity.ActivityOrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description 创建抽奖订单聚合根
 * @since 2025/12/22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderAggregate {

    /** 用户ID */
    private String userId;

    /** 活动ID */
    private Long activityId;

    /** 总次数 */
    private Integer totalCount;

    /** 月次数 */
    private Integer monthCount;

    /** 日次数 */
    private Integer dayCount;

    /** 活动订单实体对象 */
    private ActivityOrderEntity activityOrderEntity;

}
