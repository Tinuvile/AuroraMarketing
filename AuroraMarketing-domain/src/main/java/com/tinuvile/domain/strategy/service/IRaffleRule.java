package com.tinuvile.domain.strategy.service;


import com.tinuvile.domain.strategy.model.valobj.RuleWeightVO;

import java.util.List;
import java.util.Map;

/**
 * @author Tinuvile
 * @description 抽奖规则接口，提供对规则的业务功能查询
 * @since 2026/1/13
 */
public interface IRaffleRule {

    /**
     * 根据规则树ID集合查询奖品中加锁数量的配置
     *
     * @param treeIds 奖品规则树ID值
     * @return 解锁所需抽奖次数映射，键为奖品规则树ID，值为加锁值 rule_lock
     */
    Map<String, Integer> queryAwardRuleLockCount(String[] treeIds);

    /**
     * 查询奖品权重配置
     *
     * @param strategyId 策略ID
     * @return 权重规则
     */
    List<RuleWeightVO> queryAwardRuleWeight(Long strategyId);

    /**
     * 查询奖品权重配置
     *
     * @param activityId 活动ID
     * @return 权重规则
     */
    List<RuleWeightVO> queryAwardRuleWeightByActivityId(Long activityId);

}
