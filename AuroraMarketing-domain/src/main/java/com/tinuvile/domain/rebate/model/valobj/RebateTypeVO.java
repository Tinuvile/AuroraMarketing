package com.tinuvile.domain.rebate.model.valobj;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Tinuvile
 * @description 返利类型（sku活动库存充值商品，integral用户活动积分）
 * @since 2026/1/20
 */
@Getter
@AllArgsConstructor
public enum RebateTypeVO {

    SKU("sku", "活动库存充值商品"),
    INTEGRAL("integral", "用户活动积分")
    ;

    private final String code;
    private final String info;

}
