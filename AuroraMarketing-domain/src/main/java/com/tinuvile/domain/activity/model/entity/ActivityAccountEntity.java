package com.tinuvile.domain.activity.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description 活动账户（总）实体类
 * @since 2025/12/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityAccountEntity {

    /** 用户ID */
    private String userId;

    /** 活动ID */
    private Long activityId;

    /** 总次数 */
    private Integer totalCount;

    /** 总次数 - 剩余 */
    private Integer totalCountSurplus;

    /** 月次数 */
    private Integer monthCount;

    /** 月次数 - 剩余 */
    private Integer monthCountSurplus;

    /** 日次数 */
    private Integer dayCount;

    /** 日次数 - 剩余 */
    private Integer dayCountSurplus;

}
