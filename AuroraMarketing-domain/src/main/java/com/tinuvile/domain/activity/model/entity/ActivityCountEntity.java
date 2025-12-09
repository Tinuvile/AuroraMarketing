package com.tinuvile.domain.activity.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description 活动次数实体对象
 * @since 2025/12/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityCountEntity {

    /** 活动次数编号 */
    private Long activityCountId;

    /** 总次数 */
    private Integer totalCount;

    /** 月次数 */
    private Integer monthCount;

    /** 日次数 */
    private Integer dayCount;

}
