package com.tinuvile.domain.credit.service;


import com.tinuvile.domain.credit.model.entity.TradeEntity;

/**
 * @author Tinuvile
 * @description 积分调额接口【正逆向，增减积分】
 * @since 2026/1/27
 */
public interface ICreditAdjustService {

    /**
     * 创建增加积分额度订单
     *
     * @param tradeEntity 交易实体
     * @return 订单ID
     */
    String createOrder(TradeEntity tradeEntity);

}
