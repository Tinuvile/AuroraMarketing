package com.tinuvile.infrastructure.presistent.repository;


import com.tinuvile.domain.strategy.model.entity.StrategyAwardEntity;
import com.tinuvile.domain.strategy.model.entity.StrategyEntity;
import com.tinuvile.domain.strategy.model.entity.StrategyRuleEntity;
import com.tinuvile.domain.strategy.repository.IStrategyRepository;
import com.tinuvile.infrastructure.presistent.dao.IStrategyAwardDao;
import com.tinuvile.infrastructure.presistent.dao.IStrategyDao;
import com.tinuvile.infrastructure.presistent.dao.IStrategyRuleDao;
import com.tinuvile.infrastructure.presistent.po.Strategy;
import com.tinuvile.infrastructure.presistent.po.StrategyAward;
import com.tinuvile.infrastructure.presistent.po.StrategyRule;
import com.tinuvile.infrastructure.redis.IRedisService;
import com.tinuvile.types.common.Constants;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Tinuvile
 * @description 策略服务仓储实现
 * @since 2025/11/23
 */
@Repository
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IStrategyDao strategyDao;
    @Resource
    private IStrategyAwardDao strategyAwardDao;
    @Resource
    private IStrategyRuleDao strategyRuleDao;
    @Resource
    private IRedisService redisService;

    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {

        // 优先从缓存中获取
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;
        List<StrategyAwardEntity> strategyAwardEntities = redisService.getValue(cacheKey);
        if (null != strategyAwardEntities && !strategyAwardEntities.isEmpty()) return strategyAwardEntities;

        // 从库中获取数据
        List<StrategyAward> strategyAwards = strategyAwardDao.queryStrategyAwardListByStrategyId(strategyId);
        if (null == strategyAwards || strategyAwards.isEmpty()) return Collections.emptyList();
        strategyAwardEntities = new ArrayList<>(strategyAwards.size());
        for (StrategyAward strategyAward : strategyAwards) {
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder()
                    .strategyId(strategyAward.getStrategyId())
                    .awardId(strategyAward.getAwardId())
                    .awardCount(strategyAward.getAwardCount())
                    .awardCountSurplus(strategyAward.getAwardCountSurplus())
                    .awardRate(strategyAward.getAwardRate())
                    .build();
            strategyAwardEntities.add(strategyAwardEntity);
        }
        redisService.setValue(cacheKey, strategyAwardEntities);

        return strategyAwardEntities;

    }

    @Override
    public void storeStrategyAwardSearchRateTable(String key, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable) {

        // 先存抽奖策略范围值，用于抽奖时生成随机数
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key, rateRange);

        // 存储概率查找表 - 先获取 Redis 中已存在的 Map 集合，然后将新数据批量合并进去
        Map<Integer, Integer> cacheRateTable = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key);
        // cacheRateTable.putAll(strategyAwardSearchRateTable);
        // 分批处理，每批1000条
        int batchSize = 1000;
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(strategyAwardSearchRateTable.entrySet());
        for (int i = 0; i < entries.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, entries.size());
            Map<Integer, Integer> batch = new HashMap<>();
            for (int j = i; j < endIndex; j++) {
                Map.Entry<Integer, Integer> entry = entries.get(j);
                batch.put(entry.getKey(), entry.getValue());
            }
            cacheRateTable.putAll(batch);
        }

    }

    @Override
    public int getRateRange(Long strategyId) {
        return getRateRange(String.valueOf(strategyId));
    }

    @Override
    public int getRateRange(String key) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key);
    }

    @Override
    public Integer getStrategyAwardAssemble(String key, Integer rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key, rateKey);
    }

    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        String cacheKey = Constants.RedisKey.STRATEGY_KEY + strategyId;
        StrategyEntity strategyEntity = redisService.getValue(cacheKey);
        if (null != strategyEntity) return strategyEntity;
        Strategy strategy = strategyDao.queryStrategyByStrategyId(strategyId);
        if (null == strategy) return null;
        strategyEntity = StrategyEntity.builder()
                .strategyId(strategy.getStrategyId())
                .strategyDesc(strategy.getStrategyDesc())
                .ruleModels(strategy.getRuleModels())
                .build();
        redisService.setValue(cacheKey, strategyEntity);
        return strategyEntity;
    }

    @Override
    public StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel) {
        StrategyRule strategyRuleReq = new StrategyRule();
        strategyRuleReq.setStrategyId(strategyId);
        strategyRuleReq.setRuleModel(ruleModel);
        StrategyRule strategyRuleRes = strategyRuleDao.queryStrategyRule(strategyRuleReq);
        if (null == strategyRuleRes) return null;
        return StrategyRuleEntity.builder()
                .strategyId(strategyRuleRes.getStrategyId())
                .awardId(strategyRuleRes.getAwardId())
                .ruleType(strategyRuleRes.getRuleType())
                .ruleModel(strategyRuleRes.getRuleModel())
                .ruleValue(strategyRuleRes.getRuleValue())
                .ruleDesc(strategyRuleRes.getRuleDesc())
                .build();
    }

}
