package com.tinuvile.domain.credit.repository;


import com.tinuvile.domain.credit.model.aggregate.TradeAggregate;
import com.tinuvile.domain.credit.model.entity.CreditAccountEntity;

/**
 * @author Tinuvile
 * @description 用户积分仓储
 * @since 2026/1/27
 */
public interface ICreditRepository {

    void saveUserCreditTradeOrder(TradeAggregate tradeAggregate);

    CreditAccountEntity queryUserCreditAccount(String userId);

}
