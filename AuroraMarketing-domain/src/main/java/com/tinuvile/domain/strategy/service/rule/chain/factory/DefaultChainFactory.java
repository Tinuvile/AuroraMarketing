package com.tinuvile.domain.strategy.service.rule.chain.factory;


import com.tinuvile.domain.strategy.model.entity.StrategyEntity;
import com.tinuvile.domain.strategy.repository.IStrategyRepository;
import com.tinuvile.domain.strategy.service.rule.chain.ILogicChain;
import lombok.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tinuvile
 * @description 默认责任链工厂
 * @since 2025/11/27
 */
@Service
public class DefaultChainFactory {

    private final Map<Long, ILogicChain> strategyChainGroup;

    protected IStrategyRepository repository;

    // 原型模式获取对象
    private final ApplicationContext applicationContext;

    public DefaultChainFactory(IStrategyRepository repository, ApplicationContext applicationContext) {
        this.strategyChainGroup = new ConcurrentHashMap<>();
        this.repository = repository;
        this.applicationContext = applicationContext;
    }

    /**
     * 根据策略ID构建责任链
     *
     * @param strategyId 策略ID
     * @return 责任链
     */
    public ILogicChain openLogicChain(Long strategyId) {

        ILogicChain cacheLogicChain = strategyChainGroup.get(strategyId);
        if (null != cacheLogicChain) return cacheLogicChain;

        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategy.ruleModels();

        // 若未配置规则模型，则只装填一个默认责任链
        if (null == ruleModels || 0 == ruleModels.length) {
            ILogicChain ruleDefaultLogicChain = applicationContext.getBean(LogicModel.RULE_DEFAULT.getCode(), ILogicChain.class);
            // 写入缓存
            strategyChainGroup.put(strategyId, ruleDefaultLogicChain);
            return ruleDefaultLogicChain;
        }

        // 若配置了规则模型，则根据规则模型顺序构建责任链
        ILogicChain logicChain = applicationContext.getBean(ruleModels[0], ILogicChain.class);
        ILogicChain current = logicChain;
        for (int i = 1; i < ruleModels.length; i++) {
            ILogicChain nextChain = applicationContext.getBean(ruleModels[i], ILogicChain.class);
            current = current.appendNext(nextChain);
        }

        current.appendNext(applicationContext.getBean(LogicModel.RULE_DEFAULT.getCode(), ILogicChain.class));
        // 写入缓存
        strategyChainGroup.put(strategyId, logicChain);

        return logicChain;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 奖励ID */
        private Integer awardId;
        /** 规则模型 */
        private String logicModel;
    }

    @Getter
    @AllArgsConstructor
    public enum LogicModel {
        RULE_DEFAULT("rule_default", "默认抽奖"),
        RULE_BLACKLIST("rule_blacklist", "黑名单抽奖"),
        RULE_WEIGHT("rule_weight", "权重规则"),
        ;

        private final String code;
        private final String info;
    }

}
