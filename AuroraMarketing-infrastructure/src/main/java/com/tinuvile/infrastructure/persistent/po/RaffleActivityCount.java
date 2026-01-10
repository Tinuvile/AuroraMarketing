package com.tinuvile.infrastructure.persistent.po;


import lombok.Data;

import java.util.Date;

/**
 * @author Tinuvile
 * @description 抽奖活动次数配置表 持久化对象
 * @since 2025/12/8
 */
@Data
public class RaffleActivityCount {

    /** 自增ID */
    private Long id;

    /** 活动次数编号 */
    private Long activityCountId;

    /** 总次数 */
    private Integer totalCount;

    /** 月次数 */
    private Integer monthCount;

    /** 日次数 */
    private Integer dayCount;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

}
