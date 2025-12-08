package com.tinuvile.infrastructure.presistent.po;


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

    /** 订单状态（not_used、used、expire） */
    private String status;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

}
