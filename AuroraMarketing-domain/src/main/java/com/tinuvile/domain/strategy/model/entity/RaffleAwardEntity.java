package com.tinuvile.domain.strategy.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description 奖品信息实体类
 * @since 2025/11/25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardEntity {

    private Long strategyId;

    /** 抽奖奖品ID - 内部流转使用 */
    private Integer awardId;

    /** 奖品对接标识 - 对应发奖策略 */
    private String awardKey;

    /** 奖品配置信息 */
    private String awardConfig;

    /** 奖品内容描述 */
    private String awardDesc;

}
