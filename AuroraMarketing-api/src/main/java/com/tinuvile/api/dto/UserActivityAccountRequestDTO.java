package com.tinuvile.api.dto;


import lombok.Data;

/**
 * @author Tinuvile
 * @description 用户活动账户查询请求对象
 * @since 2026/1/21
 */
@Data
public class UserActivityAccountRequestDTO {

    /** 用户ID */
    private String userId;

    /** 活动ID */
    private Long activityId;

}
