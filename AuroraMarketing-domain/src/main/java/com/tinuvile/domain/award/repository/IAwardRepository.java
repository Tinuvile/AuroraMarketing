package com.tinuvile.domain.award.repository;


import com.tinuvile.domain.award.model.aggregate.UserAwardRecordAggregate;

/**
 * @author Tinuvile
 * @description 奖品仓储服务
 * @since 2026/1/11
 */
public interface IAwardRepository {

    void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate);

}
