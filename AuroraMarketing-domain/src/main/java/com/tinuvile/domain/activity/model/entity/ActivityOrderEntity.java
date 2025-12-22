package com.tinuvile.domain.activity.model.entity;


import com.tinuvile.domain.activity.model.valobj.OrderStateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Tinuvile
 * @description 活动参与实体对象
 * @since 2025/12/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityOrderEntity {

    /** 用户ID */
    private String userId;

    /** 商品SKU */
    private Long sku;

    /** 活动ID */
    private Long activityId;

    /** 活动名称 */
    private String activityName;

    /** 策略ID */
    private Long strategyId;

    /** 订单ID */
    private String orderId;

    /** 下单时间 */
    private Date orderTime;

    /** 总次数 */
    private Integer totalCount;

    /** 月次数 */
    private Integer monthCount;

    /** 日次数 */
    private Integer dayCount;

    /** 订单状态（complete） */
    private OrderStateVO state;

    /** 业务防重ID - 外部透传，确保幂等 */
    private String outBusinessNo;

}
