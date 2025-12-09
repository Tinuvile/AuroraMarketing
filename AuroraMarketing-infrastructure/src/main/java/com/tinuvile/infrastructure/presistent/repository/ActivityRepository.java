package com.tinuvile.infrastructure.presistent.repository;


import com.tinuvile.domain.activity.model.entity.ActivityCountEntity;
import com.tinuvile.domain.activity.model.entity.ActivityEntity;
import com.tinuvile.domain.activity.model.entity.ActivitySkuEntity;
import com.tinuvile.domain.activity.model.valobj.ActivityStateVO;
import com.tinuvile.domain.activity.repository.IActivityRepository;
import com.tinuvile.infrastructure.presistent.dao.IRaffleActivityCountDao;
import com.tinuvile.infrastructure.presistent.dao.IRaffleActivityDao;
import com.tinuvile.infrastructure.presistent.dao.IRaffleActivitySkuDao;
import com.tinuvile.infrastructure.presistent.po.RaffleActivity;
import com.tinuvile.infrastructure.presistent.po.RaffleActivityCount;
import com.tinuvile.infrastructure.presistent.po.RaffleActivitySku;
import com.tinuvile.infrastructure.redis.IRedisService;
import com.tinuvile.types.common.Constants;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author Tinuvile
 * @description 活动仓储服务
 * @since 2025/12/9
 */
@Repository
public class ActivityRepository implements IActivityRepository {

    @Resource
    private IRedisService redisService;
    @Resource
    private IRaffleActivityDao raffleActivityDao;
    @Resource
    private IRaffleActivitySkuDao raffleActivitySkuDao;
    @Resource
    private IRaffleActivityCountDao raffleActivityCountDao;

    @Override
    public ActivitySkuEntity queryActivitySku(Long sku) {
        RaffleActivitySku raffleActivitySku = raffleActivitySkuDao.queryActivitySku(sku);
        return ActivitySkuEntity.builder()
                .sku(raffleActivitySku.getSku())
                .activityId(raffleActivitySku.getActivityId())
                .activityCountId(raffleActivitySku.getActivityCountId())
                .stockCount(raffleActivitySku.getStockCount())
                .stockCountSurplus(raffleActivitySku.getStockCountSurplus())
                .build();
    }

    @Override
    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
        // 从Redis中查询活动
        String cacheKey = Constants.RedisKey.ACTIVITY_KEY + activityId;
        ActivityEntity activityEntity = redisService.getValue(cacheKey);
        if (null != activityEntity) return activityEntity;
        // 从数据库中查询活动
        RaffleActivity raffleActivity = raffleActivityDao.queryRaffleActivityByActivityId(activityId);
        activityEntity = ActivityEntity.builder()
                .activityId(raffleActivity.getActivityId())
                .activityName(raffleActivity.getActivityName())
                .activityDesc(raffleActivity.getActivityDesc())
                .beginDateTime(raffleActivity.getBeginDateTime())
                .endDateTime(raffleActivity.getEndDateTime())
                .strategyId(raffleActivity.getStrategyId())
                .state(ActivityStateVO.valueOf(raffleActivity.getState()))
                .build();
        // 缓存到Redis
        redisService.setValue(cacheKey, activityEntity);
        return activityEntity;
    }

    @Override
    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
        // 优先从缓存中获取
        String cacheKey = Constants.RedisKey.ACTIVITY_COUNT_KEY + activityCountId;
        ActivityCountEntity activityCountEntity = redisService.getValue(cacheKey);
        if (null != activityCountEntity) return activityCountEntity;
        // 从库中查询数据
        RaffleActivityCount raffleActivityCount = raffleActivityCountDao.queryRaffleActivityCountByActivityId(activityCountId);
        activityCountEntity = ActivityCountEntity.builder()
                .activityCountId(raffleActivityCount.getActivityCountId())
                .totalCount(raffleActivityCount.getTotalCount())
                .monthCount(raffleActivityCount.getMonthCount())
                .dayCount(raffleActivityCount.getDayCount())
                .build();
        redisService.setValue(cacheKey, activityCountEntity);
        return activityCountEntity;
    }

}
