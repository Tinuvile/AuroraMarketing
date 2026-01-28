package com.tinuvile.domain.activity.model.valobj;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Tinuvile
 * @description 订单状态枚举值对象
 * @since 2025/12/9
 */
@Getter
@AllArgsConstructor
public enum OrderStateVO {

    complete("complete", "完成"),
    wait_pay("wait_pay", "待支付"),
    ;

    private final String code;
    private final String desc;

}
