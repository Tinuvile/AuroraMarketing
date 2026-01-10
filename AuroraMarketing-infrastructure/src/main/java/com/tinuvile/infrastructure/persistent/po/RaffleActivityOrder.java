package com.tinuvile.infrastructure.persistent.po;


import lombok.Data;

import java.util.Date;

/**
 * @author Tinuvile
 * @description 抽奖活动单 持久化对象
 * @since 2025/12/8
 */
@Data
public class RaffleActivityOrder {

    /** 自增ID */
    private Long id;

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
    private String state;

    /** 业务防重ID - 外部透传，确保幂等 */
    private String outBusinessNo;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

}
