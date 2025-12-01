package com.tinuvile.domain.strategy.service.rule.tree.impl;


import com.tinuvile.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.tinuvile.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.tinuvile.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Tinuvile
 * @description 库存扣减节点
 * @since 2025/11/30
 */
@Slf4j
@Component("rule_stock")
public class RuleStockLogicTreeNode implements ILogicTreeNode {

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckTypeVO(RuleLogicCheckTypeVO.TAKE_OVER)
                .strategyAwardVO(DefaultTreeFactory.StrategyAwardVO.builder()
                        .awardId(awardId)
                        .awardRuleValue("")
                        .build())
                .build();
    }

}
