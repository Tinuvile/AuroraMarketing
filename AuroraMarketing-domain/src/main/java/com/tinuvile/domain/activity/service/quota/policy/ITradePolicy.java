package com.tinuvile.domain.activity.service.quota.policy;


import com.tinuvile.domain.activity.model.aggregate.CreateQuotaOrderAggregate;

/**
 * @author Tinuvile
 * @description 交易策略接口，包括：返利兑换【不用支付】，积分订单【需要支付】
 * @since 2026/1/28
 */
public interface ITradePolicy {

    void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate);

}
