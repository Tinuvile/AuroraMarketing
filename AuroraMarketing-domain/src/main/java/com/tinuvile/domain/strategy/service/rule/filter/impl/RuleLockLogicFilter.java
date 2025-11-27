package com.tinuvile.domain.strategy.service.rule.filter.impl;


import com.tinuvile.domain.strategy.model.entity.RuleActionEntity;
import com.tinuvile.domain.strategy.model.entity.RuleMatterEntity;
import com.tinuvile.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.tinuvile.domain.strategy.repository.IStrategyRepository;
import com.tinuvile.domain.strategy.service.annotation.LogicStrategy;
import com.tinuvile.domain.strategy.service.rule.filter.ILogicFilter;
import com.tinuvile.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Tinuvile
 * @description 抽奖n次后，解锁对应奖品
 * @since 2025/11/26
 */
@Slf4j
@Component
@LogicStrategy(logicModel = DefaultLogicFactory.LogicModel.RULE_LOCK)
public class RuleLockLogicFilter implements ILogicFilter<RuleActionEntity.RaffleCenterEntity> {

    @Resource
    private IStrategyRepository repository;

    public Long userRaffleCount = 0L;

    @Override
    public RuleActionEntity<RuleActionEntity.RaffleCenterEntity> filter(RuleMatterEntity ruleMatterEntity) {

        log.info("规则过滤 - 次数锁, userId: {}, strategyId: {}, ruleModel: {}",
                ruleMatterEntity.getUserId(), ruleMatterEntity.getStrategyId(), ruleMatterEntity.getRuleModel());

        // 查询规则配置
        String ruleValue = repository.queryStrategyRuleValue(ruleMatterEntity.getStrategyId(), ruleMatterEntity.getAwardId(), ruleMatterEntity.getRuleModel());
        long raffleCount = Long.parseLong(ruleValue);

        // 规则放行
        if (userRaffleCount >= raffleCount) {
            return RuleActionEntity.<RuleActionEntity.RaffleCenterEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        }

        // 规则拦截
        return RuleActionEntity.<RuleActionEntity.RaffleCenterEntity>builder()
                .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                .build();

    }

}
