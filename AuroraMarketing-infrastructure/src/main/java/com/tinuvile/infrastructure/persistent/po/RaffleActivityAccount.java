package com.tinuvile.infrastructure.persistent.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Tinuvile
 * @description 抽奖活动账户表 持久化对象
 * @since 2025/12/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleActivityAccount {

    /** 自增ID */
    private Long id;

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

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

}
