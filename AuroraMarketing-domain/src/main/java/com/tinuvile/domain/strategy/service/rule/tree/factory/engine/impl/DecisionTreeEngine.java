package com.tinuvile.domain.strategy.service.rule.tree.factory.engine.impl;


import com.tinuvile.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.tinuvile.domain.strategy.model.valobj.RuleTreeNodeLineVO;
import com.tinuvile.domain.strategy.model.valobj.RuleTreeNodeVO;
import com.tinuvile.domain.strategy.model.valobj.RuleTreeVO;
import com.tinuvile.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.tinuvile.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.tinuvile.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author Tinuvile
 * @description 决策树引擎
 * @since 2025/11/30
 */
@Slf4j
public class DecisionTreeEngine implements IDecisionTreeEngine {

    private final Map<String, ILogicTreeNode> logicTreeNodeGroup;

    private final RuleTreeVO ruleTreeVO;

    public DecisionTreeEngine(Map<String, ILogicTreeNode> logicTreeNodeGroup, RuleTreeVO ruleTreeVO) {
        this.logicTreeNodeGroup = logicTreeNodeGroup;
        this.ruleTreeVO = ruleTreeVO;
    }

    @Override
    public DefaultTreeFactory.StrategyAwardData process(String userId, Long strategyId, Integer awardId) {
        DefaultTreeFactory.StrategyAwardData strategyAwardData = null;

        // 获取基础信息
        String nextNode = ruleTreeVO.getTreeRootRuleNode();
        Map<String, RuleTreeNodeVO> treeNodeMap = ruleTreeVO.getTreeNodeMap();

        // 获取起始节点
        RuleTreeNodeVO ruleTreeNode = treeNodeMap.get(nextNode);
        while (null != nextNode) {
            // 获取决策节点
            ILogicTreeNode logicTreeNode = logicTreeNodeGroup.get(ruleTreeNode.getRuleKey());

            // 决策节点计算
            DefaultTreeFactory.TreeActionEntity logicEntity = logicTreeNode.logic(userId, strategyId, awardId);
            RuleLogicCheckTypeVO ruleLogicCheckTypeVO = logicEntity.getRuleLogicCheckTypeVO();
            strategyAwardData = logicEntity.getStrategyAwardData();
            log.info("决策树引擎【{}】 treeId:{} node:{} code:{}",
                    ruleTreeVO.getTreeName(), ruleTreeVO.getTreeId(), nextNode, ruleLogicCheckTypeVO.getCode());

            // 获取下一个节点
            nextNode = nextNode(ruleLogicCheckTypeVO.getCode(), ruleTreeNode.getTreeNodeLineVOList());
            ruleTreeNode = treeNodeMap.get(nextNode);
        }

        // 返回最终结果
        return strategyAwardData;

    }

    public String nextNode(String matterValue, List<RuleTreeNodeLineVO> treeNodeLineVOList) {
        if (null == treeNodeLineVOList || treeNodeLineVOList.isEmpty()) return null;
        for (RuleTreeNodeLineVO nodeLine : treeNodeLineVOList) {
            if (decisionLogic(matterValue, nodeLine)) {
                return nodeLine.getRuleNodeTo();
            }
        }
        throw new RuntimeException("决策树引擎，nextNode计算失败，未找到可执行节点");
    }

    public boolean decisionLogic(String matterValue, RuleTreeNodeLineVO nodeLine) {
        switch (nodeLine.getRuleLimitType()) {
            case EQUAL:
                return matterValue.equals(nodeLine.getRuleLimitValue().getCode());
            case GREATER_THAN:
            case LESS_THAN:
            case GREATER_EQUAL:
            case LESS_EQUAL:
            default:
                return false;
        }
    }

}
