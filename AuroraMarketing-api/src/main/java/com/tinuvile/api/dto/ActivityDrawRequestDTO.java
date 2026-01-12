package com.tinuvile.api.dto;


import lombok.Data;

/**
 * @author Tinuvile
 * @description 活动抽奖请求对象
 * @since 2026/1/12
 */
@Data
public class ActivityDrawRequestDTO {

    /** 用户ID */
    private String userId;

    /** 活动ID */
    private Long activityId;

}
