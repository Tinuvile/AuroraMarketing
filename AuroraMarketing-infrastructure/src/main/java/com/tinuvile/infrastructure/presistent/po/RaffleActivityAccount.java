package com.tinuvile.infrastructure.presistent.po;


import lombok.Data;

import java.util.Date;

/**
 * @author Tinuvile
 * @description 抽奖活动账户表 持久化对象
 * @since 2025/12/8
 */
@Data
public class RaffleActivityAccount {

    /** 自增ID */
    private Long id;

    /** 用户ID */
    private String userId;

    /** 活动ID */
    private Long activityId;

    /** 总次数 */
    private Integer totalCount;

    /** 总次数 */
    private Integer totalCountSurplus;

    /** 月次数 */
    private Integer monthCount;

    /** 月次数 */
    private Integer monthCountSurplus;

    /** 日次数 */
    private Integer dayCount;

    /** 日次数 */
    private Integer dayCountSurplus;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

}
