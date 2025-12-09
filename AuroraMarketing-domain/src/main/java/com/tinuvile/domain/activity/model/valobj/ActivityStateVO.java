package com.tinuvile.domain.activity.model.valobj;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Tinuvile
 * @description 活动状态枚举值对象
 * @since 2025/12/9
 */
@Getter
@AllArgsConstructor
public enum ActivityStateVO {

    create("create", "创建"),
    open("open", "开启"),
    close("close", "关闭"),
    ;

    private final String code;
    private final String info;

}
