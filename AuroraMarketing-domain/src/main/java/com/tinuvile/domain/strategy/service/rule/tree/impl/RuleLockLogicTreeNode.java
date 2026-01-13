package com.tinuvile.domain.strategy.service.rule.tree.impl;


import com.tinuvile.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.tinuvile.domain.strategy.repository.IStrategyRepository;
import com.tinuvile.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.tinuvile.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Tinuvile
 * @description 次数锁节点
 * @since 2025/11/30
 */
@Slf4j
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {

    @Resource
    private IStrategyRepository repository;

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue, Date endDateTime) {

        log.info("规则过滤 - 次数锁 userId: {} strategyId: {} awardId: {} ruleValue: {}", userId, strategyId, awardId, ruleValue);

        long raffleCount = 0L;
        try {
            raffleCount = Long.parseLong(ruleValue);
        } catch (Exception e) {
            throw new RuntimeException("规则过滤 - 次数锁异常 ruleValue: " + ruleValue + " 配置不正确");
        }

        // 查询用户抽奖次数
        Integer userRaffleCount = repository.queryTodayUserRaffleCount(userId, strategyId);

        // 用户抽奖次数大于规则限定值，规则放行
        if (userRaffleCount >= raffleCount) {
            log.info("规则过滤 - 次数锁【放行】 userId:{} strategyId:{} awardId:{} raffleCount:{} userRaffleCount:{}", userId, strategyId, awardId, raffleCount, userRaffleCount);
            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckTypeVO(RuleLogicCheckTypeVO.ALLOW)
                    .build();
        }

        // 用户抽奖次数小于规则限定值，规则拦截
        log.info("规则过滤 - 次数锁【拦截】 userId:{} strategyId:{} awardId:{} raffleCount:{} userRaffleCount:{}", userId, strategyId, awardId, raffleCount, userRaffleCount);
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckTypeVO(RuleLogicCheckTypeVO.TAKE_OVER)
                .build();

    }

}
