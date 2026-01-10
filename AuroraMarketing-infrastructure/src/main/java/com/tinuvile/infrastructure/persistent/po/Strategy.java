package com.tinuvile.infrastructure.persistent.po;


import lombok.Data;

import java.util.Date;

/**
 * @author Tinuvile
 * @description 策略总表
 * @since 2025/11/23
 */
@Data
public class Strategy {

    /** 自增ID */
    private Long id;

    /** 策略ID */
    private Long strategyId;

    /** 策略描述 */
    private String strategyDesc;

    /** 规则模型 */
    private String ruleModels ;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

}
