package com.tinuvile.domain.strategy.service.rule.tree.factory;


import com.tinuvile.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.tinuvile.domain.strategy.model.valobj.RuleTreeVO;
import com.tinuvile.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.tinuvile.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import com.tinuvile.domain.strategy.service.rule.tree.factory.engine.impl.DecisionTreeEngine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Tinuvile
 * @description 规则树工厂
 * @since 2025/11/30
 */
@Service
public class DefaultTreeFactory {

    private final Map<String, ILogicTreeNode> logicTreeNodeGroup;

    public DefaultTreeFactory(Map<String, ILogicTreeNode> logicTreeNodeGroup) {
        this.logicTreeNodeGroup = logicTreeNodeGroup;
    }

    public IDecisionTreeEngine openLogicTree(RuleTreeVO ruleTreeVO) {
        return new DecisionTreeEngine(logicTreeNodeGroup, ruleTreeVO);
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TreeActionEntity {
        /** 规则逻辑检查类型 */
        private RuleLogicCheckTypeVO ruleLogicCheckTypeVO;
        /** 策略奖品数据 */
        private StrategyAwardData strategyAwardData;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardData {
        /** 奖品ID */
        private Integer awardId;
        /** 奖品规则值 */
        private String awardRuleValue;
    }

}
