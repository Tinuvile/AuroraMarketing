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

    /** 抽奖奖品ID - 内部流转使用 */
    private Integer awardId;

    /** 奖品配置信息 */
    private String awardConfig;

    /** 奖品排序 */
    private Integer sort;

}
