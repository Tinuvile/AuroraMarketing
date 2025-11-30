package com.tinuvile.domain.strategy.model.valobj;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Tinuvile
 * @description 规则树对象
 * @since 2025/11/30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleTreeVO {

    /** 规则树ID */
    private Integer treeId;

    /** 规则树名称 */
    private String treeName;

    /** 规则树描述 */
    private String treeDesc;

    /** 规则树根节点 */
    private String treeRootRuleNode;

    /** 规则节点映射 */
    private Map<String, RuleTreeNodeVO> treeNodeMap;

}
