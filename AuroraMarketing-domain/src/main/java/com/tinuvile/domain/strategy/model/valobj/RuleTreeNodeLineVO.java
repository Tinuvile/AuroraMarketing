package com.tinuvile.domain.strategy.model.valobj;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description 规则树节点指向线对象
 * @since 2025/11/30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleTreeNodeLineVO {

    /** 规则树ID */
    private String treeId;

    /** 规则节点From */
    private String ruleNodeFrom;

    /** 规则节点To */
    private String ruleNodeTo;

    /** 规则限制类型 */
    private RuleLimitTypeVO ruleLimitType;

    /** 规则限制值 */
    private RuleLogicCheckTypeVO ruleLimitValue;

}
