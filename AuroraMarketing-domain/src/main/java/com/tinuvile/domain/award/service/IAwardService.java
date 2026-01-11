package com.tinuvile.domain.award.service;


import com.tinuvile.domain.award.model.entity.UserAwardRecordEntity;

/**
 * @author Tinuvile
 * @description 奖品服务接口
 * @since 2026/1/11
 */
public interface IAwardService {

    void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity);

}
