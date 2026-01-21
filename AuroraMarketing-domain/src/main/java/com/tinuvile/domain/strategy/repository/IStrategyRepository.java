package com.tinuvile.domain.strategy.repository;


import com.tinuvile.domain.strategy.model.entity.StrategyAwardEntity;
import com.tinuvile.domain.strategy.model.entity.StrategyEntity;
import com.tinuvile.domain.strategy.model.entity.StrategyRuleEntity;
import com.tinuvile.domain.strategy.model.valobj.RuleTreeVO;
import com.tinuvile.domain.strategy.model.valobj.RuleWeightVO;
import com.tinuvile.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import com.tinuvile.domain.strategy.model.valobj.StrategyAwardStockKeyVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Tinuvile
 * @description 策略服务仓储接口
 * @since 2025/11/23
 */
public interface IStrategyRepository {

    /**
     * 查询策略下的奖品列表
     *
     * @param strategyId 策略ID
     * @return 策略下的奖品列表
     */
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    /**
     * 缓存策略奖品搜索概率表到 Redis
     *
     * @param key 缓存键
     * @param rateRange 概率范围
     * @param shuffleStrategyAwardSearchRateTable 随机策略奖品搜索概率表
     */
    void storeStrategyAwardSearchRateTable(String key, Integer rateRange, Map<Integer, Integer> shuffleStrategyAwardSearchRateTable);

    /**
     * 查询策略奖品搜索概率范围
     *
     * @param strategyId 策略ID
     * @return 概率范围
     */
    int getRateRange(Long strategyId);

     /**
     * 查询策略奖品搜索概率范围
     *
     * @param key 缓存键
     * @return 概率范围
     */
    int getRateRange(String key);

    /**
     * 查询策略奖品装配值
     *
     * @param key 缓存键
     * @param rateKey 概率键
     * @return 策略奖品装配值
     */
    Integer getStrategyAwardAssemble(String key, Integer rateKey);

    /**
     * 查询策略实体
     *
     * @param strategyId 策略ID
     * @return 策略实体
     */
    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    /**
     * 查询策略规则实体
     *
     * @param strategyId 策略ID
     * @param ruleModel 规则模型
     * @return 策略规则实体
     */
    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);

    /**
     * 查询策略规则值
     *
     * @param strategyId 策略ID
     * @param awardId 奖品ID
     * @param ruleModel 规则模型
     * @return 策略规则值
     */
    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);

    /**
     * 查询策略规则值
     *
     * @param strategyId 策略ID
     * @param ruleModel 规则模型
     * @return 策略规则值
     */
    String queryStrategyRuleValue(Long strategyId, String ruleModel);

    /**
     * 查询策略奖品规则模型值对象
     *
     * @param strategyId 策略ID
     * @param awardId 奖品ID
     * @return 策略奖品规则模型值对象
     */
    StrategyAwardRuleModelVO queryStrategyAwardRuleModelVO(Long strategyId, Integer awardId);

    /**
     * 查询规则树结构信息
     *
     * @param treeId 规则树ID
     * @return 规则树值对象
     */
    RuleTreeVO queryRuleTreeVOByTreeId(String treeId);

    /**
     * 缓存策略奖品库存到 Redis
     *
     * @param cacheKey 缓存键
     * @param awardCount 奖品库存
     */
    void cacheStrategyAwardCount(String cacheKey, Integer awardCount);

    /**
     * 从 Redis 中使用 decr 方式扣减奖品库存
     *
     * @param cacheKey 缓存键
     * @return 扣减结果
     */
    Boolean subtractionAwardStock(String cacheKey);

    /**
     * 缓存key，decr方式扣减库存
     *
     * @param cacheKey 缓存键
     * @param endDateTime 活动结束时间
     * @return 扣减结果
     */
    Boolean subtractionAwardStock(String cacheKey, Date endDateTime);

    /**
     * 写入奖品库存消费队列，延迟消费更新数据库记录
     *
     * @param strategyAwardStockKeyVO 策略奖品库存键值对象
     */
    void awardStockConsumeSendQueue(StrategyAwardStockKeyVO strategyAwardStockKeyVO);

    /**
     * 获取奖品库存消费队列
     *
     * @return 策略奖品库存键值对象
     * @throws InterruptedException 线程中断异常
     */
    StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException;

    /**
     * 更新奖品库存消耗
     *
     * @param strategyId 策略ID
     * @param awardId 奖品ID
     */
    void updateStrategyAwardStock(Long strategyId, Integer awardId);

     /**
     * 查询策略奖品实体
     *
     * @param strategyId 策略ID
     * @param awardId 奖品ID
     * @return 策略奖品实体
     */
    StrategyAwardEntity queryStrategyAwardEntity(Long strategyId, Integer awardId);

    /**
     * 查询活动下的策略ID
     *
     * @param activityId 活动ID
     * @return 策略ID
     */
    Long queryStrategyIdByActivityId(Long activityId);

    /**
     * 查询用户今日抽奖次数
     *
     * @param userId 用户ID
     * @param strategyId 策略ID
     * @return 今日抽奖次数
     */
    Integer queryTodayUserRaffleCount(String userId, Long strategyId);

    /**
     * 根据规则树ID查询奖品中加锁数量的配置
     *
     * @param treeIds 奖品规则树ID值
     * @return 奖品规则锁库存数量映射，键为奖品规则树ID，值为加锁值 rule_lock
     */
    Map<String, Integer> queryAwardRuleLockCount(String[] treeIds);

    /**
     * 根据用户ID、策略ID，查询用户活动账户总使用量
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return 使用总量
     */
    Integer queryActivityAccountTotalUseCount(String userId, Long strategyId);

    /**
     * 查询奖品权重配置
     *
     * @param strategyId 策略ID
     * @return 权重规则
     */
    List<RuleWeightVO> queryAwardRuleWeight(Long strategyId);

}
