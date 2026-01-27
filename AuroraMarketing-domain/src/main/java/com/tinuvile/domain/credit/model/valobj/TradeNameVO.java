package com.tinuvile.domain.credit.model.valobj;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Tinuvile
 * @description 交易名称枚举值
 * @since 2026/1/27
 */
@Getter
@AllArgsConstructor
public enum TradeNameVO {

    REBATE("行为返利"),
    CONVERT_SKU("兑换抽奖"),
    ;

    private final String name;

}
