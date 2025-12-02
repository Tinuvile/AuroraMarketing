package com.tinuvile.domain.strategy.service.rule.tree.impl;


import com.tinuvile.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.tinuvile.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.tinuvile.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.tinuvile.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Tinuvile
 * @description 兜底奖励节点
 * @since 2025/11/30
 */
@Slf4j
@Component("rule_luck_award")
public class RuleLuckAwardLogicTreeNode implements ILogicTreeNode {

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {

        log.info("规则过滤 - 兜底奖品 userId: {} strategyId: {} awardId: {} ruleValue: {}", userId, strategyId, awardId, ruleValue);

        String[] split = ruleValue.split(Constants.COLON);
        if (split.length == 0) {
            log.error("规则过滤 - 兜底奖品，兜底奖品未配置告警 userId: {} strategyId: {} awardId: {}", userId, strategyId, awardId);
            throw new RuntimeException("兜底奖品未配置 " + ruleValue);
        }

        // 兜底奖励配置
        Integer luckAwardId = Integer.valueOf(split[0]);
        String luckAwardRuleValue = split.length > 1 ? split[1] : "";

        // 返回兜底奖品
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckTypeVO(RuleLogicCheckTypeVO.TAKE_OVER)
                .strategyAwardVO(DefaultTreeFactory.StrategyAwardVO.builder()
                        .awardId(luckAwardId)
                        .awardRuleValue(luckAwardRuleValue)
                        .build())
                .build();

    }

}
