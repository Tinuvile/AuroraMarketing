package com.tinuvile.domain.activity.model.entity;


import lombok.Data;

/**
 * @author Tinuvile
 * @description 参与抽奖活动实体对象
 * @since 2026/1/10
 */
@Data
public class PartakeRaffleActivityEntity {

    /** 用户ID */
    private String userId;

    /** 活动ID */
    private Long activityId;

}
