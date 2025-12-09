package com.tinuvile.infrastructure.presistent.dao;


import com.tinuvile.infrastructure.presistent.po.RaffleActivity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Tinuvile
 * @description 抽奖活动表Dao
 * @since 2025/12/9
 */
@Mapper
public interface IRaffleActivityDao {

    RaffleActivity queryRaffleActivityByActivityId(Long activityId);

}
