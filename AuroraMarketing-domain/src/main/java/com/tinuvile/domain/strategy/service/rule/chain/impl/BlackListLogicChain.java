package com.tinuvile.domain.strategy.service.rule.chain.impl;


import com.tinuvile.domain.strategy.repository.IStrategyRepository;
import com.tinuvile.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.tinuvile.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.tinuvile.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Tinuvile
 * @description 黑名单责任链
 * @since 2025/11/25
 */
@Slf4j
@Component("rule_blacklist")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BlackListLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyRepository repository;

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_BLACKLIST.getCode();
    }

    /**
     * 黑名单用户过滤
     * 格式为：100:user1,user2
     * 前面为黑名单专用奖品ID，后面为逗号分隔的用户ID列表
     *
     * @param userId    用户ID
     * @param strategyId 策略ID
     * @return 黑名单专用奖品ID，若用户在黑名单中则返回该ID，否则返回下一个责任链的处理结果
     */
    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {

        log.info("规则过滤 - 黑名单 userId:{} strategyId:{} ruleModel:{}",
                userId, strategyId, ruleModel());

        String ruleValue = repository.queryStrategyRuleValue(strategyId, ruleModel());
        if (ruleValue == null || ruleValue.isEmpty()) return next().logic(userId, strategyId);

        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.parseInt(splitRuleValue[0]);

        String[] userBlackIds = splitRuleValue[1].split(Constants.SPLIT);
        for (String userBlackId : userBlackIds) {
            if (userId.equals(userBlackId)) {
                log.info("抽奖责任链 - 黑名单接管 userId:{} strategyId:{} ruleModel:{} awardId:{}",
                        userId, strategyId, ruleModel(), awardId);
                return DefaultChainFactory.StrategyAwardVO.builder()
                        .awardId(awardId)
                        .logicModel(ruleModel())
                        .build();
            }
        }

        log.info("抽奖责任链 - 黑名单放行 userId:{} strategyId:{} ruleModel:{}",
                userId, strategyId, ruleModel());

        return next().logic(userId, strategyId);
    }



}
