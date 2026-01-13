package com.tinuvile.domain.strategy.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Tinuvile
 * @description 抽奖因子实体类 - 抽奖参数，封装执行抽奖所需的基础参数
 * @since 2025/11/25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleFactorEntity {

    /** 用户ID */
    private String userId;

    /** 策略ID */
    private Long strategyId;

    /** 结束时间 */
    private Date endDateTime;

}
