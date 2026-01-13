package com.tinuvile.domain.strategy.service;


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
     * @return 奖品规则锁库存数量映射，键为奖品规则树ID，值为加锁值 rule_lock
     */
    Map<String, Integer> queryAwardRuleLockCount(String[] treeIds);

}
