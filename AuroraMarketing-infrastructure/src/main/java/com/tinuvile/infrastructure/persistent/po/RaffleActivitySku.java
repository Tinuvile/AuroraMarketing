package com.tinuvile.infrastructure.persistent.po;


import lombok.Data;

import java.util.Date;

/**
 * @author Tinuvile
 * @description 抽奖活动sku持久化对象
 * @since 2025/12/9
 */
@Data
public class RaffleActivitySku {

    /** 自增ID */
    private Long id;

    /** 商品SKU - 每一个组合当作一个商品 */
    private Long sku;

    /** 活动ID */
    private Long activityId;

    /** 活动个人参与次数ID */
    private Long activityCountId;

    /** 商品库存 */
    private Integer stockCount;

    /** 剩余库存 */
    private Integer stockCountSurplus;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

}
