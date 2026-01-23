package com.tinuvile.domain.award.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description 分发奖品实体
 * @since 2026/1/23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistributeAwardEntity {

    /** 用户ID */
    private String userId;

    /** 订单ID */
    private String orderId;

    /** 奖品ID */
    private Integer awardId;

    /** 奖品配置 */
    private String awardConfig;

}
