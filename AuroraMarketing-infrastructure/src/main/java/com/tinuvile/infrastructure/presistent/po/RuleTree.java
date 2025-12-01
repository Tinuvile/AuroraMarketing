package com.tinuvile.infrastructure.presistent.po;


import lombok.Data;

import java.util.Date;

/**
 * @author Tinuvile
 * @description 规则树
 * @since 2025/12/1
 */
@Data
public class RuleTree {

    /** 自增ID */
    private Long id;

    /** 规则树ID */
    private String treeId;

    /** 规则树名称 */
    private String treeName;

    /** 规则树描述 */
    private String treeDesc;

    /** 规则树根入口规则 */
    private String treeRootRuleKey;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

}
