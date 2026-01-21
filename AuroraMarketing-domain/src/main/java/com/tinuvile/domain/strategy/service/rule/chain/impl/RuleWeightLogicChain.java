package com.tinuvile.domain.strategy.service.rule.chain.impl;


import com.tinuvile.domain.strategy.repository.IStrategyRepository;
import com.tinuvile.domain.strategy.service.armory.IStrategyDispatch;
import com.tinuvile.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.tinuvile.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.tinuvile.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Tinuvile
 * @description 权重抽奖责任链
 * @since 2025/11/25
 */
@Slf4j
@Component("rule_weight")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RuleWeightLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyRepository repository;

    @Resource
    protected IStrategyDispatch strategyDispatch;

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_WEIGHT.getCode();
    }

    /**
     * 权重责任链过滤
     *
     * @param userId 用户ID
     * @param strategyId 策略ID
     * @return 抽奖结果
     */
    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        log.info("规则过滤 - 权重范围 userId:{} strategyId:{} ruleModel:{}",
                userId, strategyId, ruleModel());

        String ruleValue = repository.queryStrategyRuleValue(strategyId, ruleModel());
        if (ruleValue == null || ruleValue.isEmpty()) return next().logic(userId, strategyId);

        // 查询规则配置并解析
        Map<Long, String> analyticalValueGroup = getAnalyticalValue(ruleValue);
        if (null == analyticalValueGroup || analyticalValueGroup.isEmpty()) {
            log.warn("抽奖责任链 - 权重告警【策略配置权重，但 ruleValue 未配置对应值】 userId:{} strategyId:{} ruleModel:{}",
                    userId, strategyId, ruleModel());
            return next().logic(userId, strategyId);
        }

        // 权重值排序
        List<Long> analyticalSortedKeys = new ArrayList<>(analyticalValueGroup.keySet());
        Collections.sort(analyticalSortedKeys);

        // 权重值筛选，筛选出小于等于用户分数的最大权重值
        Integer userScore = repository.queryActivityAccountTotalUseCount(userId, strategyId);
        Long nextValue = analyticalSortedKeys.stream()
                .filter(key -> userScore >= key)
                .max(Comparator.naturalOrder())
                .orElse(null);

        // 抽奖
        if (null != nextValue) {
            Integer awardId = strategyDispatch.getRandomAwardId(strategyId, analyticalValueGroup.get(nextValue));
            log.info("抽奖责任链 - 权重接管 userId: {} strategyId: {} ruleModel: {} awardId: {}",
                    userId, strategyId, ruleModel(), awardId);
            return DefaultChainFactory.StrategyAwardVO.builder()
                    .awardId(awardId)
                    .logicModel(ruleModel())
                    .build();
        }

        log.info("抽奖责任链 - 权重放行 userId: {} strategyId: {} ruleModel: {}",
                userId, strategyId, ruleModel());

        return next().logic(userId, strategyId);

    }

    /**
     * 解析规则值，格式为：30:103,104,105,106 100:105,106,107,108
     *
     * @param ruleValue 规则值
     * @return 解析后的Map，key为权重值，value为完整的规则键值对
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
