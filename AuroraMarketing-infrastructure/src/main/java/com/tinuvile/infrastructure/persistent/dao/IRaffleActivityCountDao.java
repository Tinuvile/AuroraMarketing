package com.tinuvile.infrastructure.persistent.dao;


import com.tinuvile.infrastructure.persistent.po.RaffleActivityCount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Tinuvile
 * @description 抽奖活动次数配置表Dao
 * @since 2025/12/9
 */
@Mapper
public interface IRaffleActivityCountDao {

    RaffleActivityCount queryRaffleActivityCountByActivityCountId(Long activityCountId);

}
