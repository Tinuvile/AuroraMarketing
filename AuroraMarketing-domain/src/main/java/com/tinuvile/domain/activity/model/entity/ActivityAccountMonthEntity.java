package com.tinuvile.domain.activity.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description 活动账户月实体
 * @since 2026/1/10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityAccountMonthEntity {

    /** 用户ID */
    private String userId;

    /** 活动ID */
    private Long activityId;

    /** 日期 (yyyy-mm) */
    private String month;

    /** 月次数 */
    private Integer monthCount;

    /** 月次数剩余 */
    private Integer monthCountSurplus;

}
