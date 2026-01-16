package com.tinuvile.domain.rebate.model.valobj;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Tinuvile
 * @description 任务状态值对象
 * @since 2026/1/16
 */
@Getter
@AllArgsConstructor
public enum TaskStateVO {

    create("create", "创建"),
    complete("complete", "发送完成"),
    fail("fail", "发送失败")
    ;

    private final String code;
    private final String state;

}
