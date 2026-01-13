package com.tinuvile.api.dto;


import lombok.Data;

/**
 * @author Tinuvile
 * @description 抽奖奖品列表请求参数
 * @since 2025/12/4
 */
@Data
public class RaffleAwardListRequestDTO {

    /** 用户ID */
    private String userId;

    /** 活动ID */
    private Long activityId;

}
