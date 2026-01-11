package com.tinuvile.domain.award.model.valobj;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Tinuvile
 * @description 奖品状态枚举值对象
 * @since 2026/1/11
 */
@Getter
@AllArgsConstructor
public enum AwardStateVO {

    create("create", "创建"),
    complete("complete", "发奖完成"),
    fail("fail", "发奖失败"),
    ;

    private final String code;
    private final String desc;

}
