package com.tinuvile.domain.strategy.service.rule.tree;


import com.tinuvile.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author Tinuvile
 * @description 规则树接口
 * @since 2025/11/30
 */
public interface ILogicTreeNode {

    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue);

}
