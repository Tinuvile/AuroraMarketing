package com.tinuvile.domain.strategy.model.valobj;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Tinuvile
 * @description 规则限定枚举值
 * @since 2025/11/30
 */
@Getter
@AllArgsConstructor
public enum RuleLimitTypeVO {

    EQUAL(1, "等于"),
    GREATER_THAN(2, "大于"),
    LESS_THAN(3, "小于"),
    GREATER_EQUAL(4, "大于等于"),
    LESS_EQUAL(5, "小于等于"),
    ENUM(6, "枚举");

    private final Integer code;
    private final String info;

}
