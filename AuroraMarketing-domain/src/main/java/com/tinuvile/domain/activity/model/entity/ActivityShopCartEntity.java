package com.tinuvile.domain.activity.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description 活动购物车实体对象
 * @since 2025/12/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityShopCartEntity {

    /** 用户ID */
    private String userId;

    /** 活动商品SKU - activity + activity count */
    private Long sku;

}
