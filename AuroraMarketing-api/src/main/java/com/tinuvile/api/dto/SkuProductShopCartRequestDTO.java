package com.tinuvile.api.dto;


import lombok.Data;

/**
 * @author Tinuvile
 * @description 商品购物车请求对象
 * @since 2026/1/29
 */
@Data
public class SkuProductShopCartRequestDTO {

    /** 用户ID */
    private String userId;

    /** 商品SKU */
    private Long sku;

}
