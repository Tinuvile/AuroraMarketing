package com.tinuvile.domain.credit.model.valobj;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Tinuvile
 * @description 交易类型枚举值
 * @since 2026/1/27
 */
@Getter
@AllArgsConstructor
public enum TradeTypeVO {

    FORWARD("forward", "正向交易， + 积分"),
    REVERSE("reverse", "逆向交易， - 积分"),
    ;

    private final String code;
    private final String info;

}
