package com.tinuvile.domain.strategy.service.rule.impl;


import com.tinuvile.domain.strategy.model.entity.RuleActionEntity;
import com.tinuvile.domain.strategy.model.entity.RuleMatterEntity;
import com.tinuvile.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.tinuvile.domain.strategy.repository.IStrategyRepository;
import com.tinuvile.domain.strategy.service.annotation.LogicStrategy;
import com.tinuvile.domain.strategy.service.rule.ILogicFilter;
import com.tinuvile.domain.strategy.service.rule.factory.DefaultLogicFactory;
import com.tinuvile.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Tinuvile
 * @description 【抽奖前置规则】 根据抽奖权重返回可抽奖范围的KEY
 * @since 2025/11/25
 */
@Slf4j
@Component
@LogicStrategy(logicModel = DefaultLogicFactory.LogicModel.RULE_WEIGHT)
public class RuleWeightLogicFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {

    @Resource
    private IStrategyRepository repository;

    public Long userScore = 4500L;

    /**
     * 权重规则过滤
     *
     * @param ruleMatterEntity 规则物料实体
     * @return 过滤后的规则动作实体
     */
    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {

        log.info("规则过滤 - 权重范围 userId:{} strategyId:{} ruleModel:{}",
                ruleMatterEntity.getUserId(), ruleMatterEntity.getStrategyId(), ruleMatterEntity.getRuleModel());

        String userId = ruleMatterEntity.getUserId();
        Long strategyId = ruleMatterEntity.getStrategyId();
        String ruleValue = repository.queryStrategyRuleValue(ruleMatterEntity.getStrategyId(), ruleMatterEntity.getAwardId(), ruleMatterEntity.getRuleModel());

        // 根据用户ID查询用户抽奖消耗的积分值
        Map<Long, String> analyticalValueGroup = getAnalyticalValue(ruleValue);
        if (null == analyticalValueGroup || analyticalValueGroup.isEmpty()) {
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        }

        // 转换 Keys 值，并默认排序
        List<Long> analyticalSortedKeys = new ArrayList<>(analyticalValueGroup.keySet());
        Collections.sort(analyticalSortedKeys);

        // 找出最小符合的值
        Long nextValue = analyticalSortedKeys.stream()
                .filter(key -> userScore >= key)
                .findFirst()
                .orElse(null);

        if (null != nextValue) {
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .data(RuleActionEntity.RaffleBeforeEntity.builder()
                            .strategyId(strategyId)
                            .ruleWeightValueKey(analyticalValueGroup.get(nextValue))
                            .build())
                    .ruleModel(DefaultLogicFactory.LogicModel.RULE_WEIGHT.getCode())
                    .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                    .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                    .build();
        }

        return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                .build();

    }

    /**
     * 解析规则值，格式为：30:103,104,105,106 100:105,106,107,108
     *
     * @param ruleValue 规则值
     * @return 解析后的Map，key为权重值，value为奖品ID
     */
    private Map<Long, String> getAnalyticalValue(String ruleValue) {
        String[] ruleValueGroups = ruleValue.split(Constants.SPACE);
        Map<Long, String> ruleValueMap = new HashMap<>();
        for (String ruleValueKey : ruleValueGroups) {
            if (ruleValueKey == null || ruleValueKey.isEmpty()) return ruleValueMap;
            String[] parts = ruleValueKey.split(Constants.COLON);
            if (parts.length != 2) throw new IllegalArgumentException("非法权重值格式：" + ruleValueKey);
            ruleValueMap.put(Long.parseLong(parts[0]), ruleValueKey);
        }
        return ruleValueMap;
    }

}
