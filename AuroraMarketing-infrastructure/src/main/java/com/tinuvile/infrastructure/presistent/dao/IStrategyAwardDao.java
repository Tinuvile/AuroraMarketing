package com.tinuvile.infrastructure.presistent.dao;


import com.tinuvile.infrastructure.presistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Tinuvile
 * @description 策略奖励表DAO
 * @since 2025/11/23
 */
@Mapper
public interface IStrategyAwardDao {

    List<StrategyAward> queryStrategyAwardList();

    List<StrategyAward> queryStrategyAwardListByStrategyId(Long strategyId);

    String queryStrategyAwardRuleModels(StrategyAward strategyAwardReq);

    void updateStrategyAwardStock(StrategyAward strategyAwardReq);

    StrategyAward queryStrategyAward(StrategyAward strategyAwardReq);

}
