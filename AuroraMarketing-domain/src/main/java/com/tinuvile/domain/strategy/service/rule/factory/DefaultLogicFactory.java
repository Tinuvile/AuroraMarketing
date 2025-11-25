package com.tinuvile.domain.strategy.service.rule.factory;


import com.tinuvile.domain.strategy.model.entity.RuleActionEntity;
import com.tinuvile.domain.strategy.service.annotation.LogicStrategy;
import com.tinuvile.domain.strategy.service.rule.ILogicFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tinuvile
 * @description 默认抽奖规则工厂
 * @since 2025/11/25
 */
@Service
public class DefaultLogicFactory {

    public Map<String, ILogicFilter<?>> logicFilterMap = new ConcurrentHashMap<>();

    public DefaultLogicFactory(List<ILogicFilter<?>> logicFilters) {
        logicFilters.forEach(logicFilter -> {
            LogicStrategy strategy = AnnotationUtils.findAnnotation(logicFilter.getClass(), LogicStrategy.class);
            if (null != strategy) {
                logicFilterMap.put(strategy.logicModel().getCode(), logicFilter);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T extends RuleActionEntity.RaffleEntity> Map<String, ILogicFilter<T>> openLogicFilter() {
        return (Map<String, ILogicFilter<T>>) (Map<?, ?>) logicFilterMap;
    }

    @Getter
    @AllArgsConstructor
    public enum LogicModel {

        RULE_WEIGHT("rule_weight", "【抽奖前置规则】根据抽奖权重返回可抽奖范围KEY"),
        RULE_BLACKLIST("rule_blacklist", "【抽奖前置规则】黑名单规则过滤，命中黑名单直接返回");
        ;

        private final String code;
        private final String info;

    }

}
