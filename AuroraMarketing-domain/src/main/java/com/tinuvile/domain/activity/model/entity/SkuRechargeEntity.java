package com.tinuvile.domain.activity.model.entity;


import com.tinuvile.domain.activity.model.valobj.OrderTradeTypeVO;
import lombok.Data;

/**
 * @author Tinuvile
 * @description 活动商品充值对象实体
 * @since 2025/12/22
 */
@Data
public class SkuRechargeEntity {

    /** 用户ID */
    private String userId;

    /** 活动商品SKU - activity + activity count */
    private Long sku;

    /** 外部业务单号，外部谁充值谁透传，保证幂等 */
    private String outBusinessNo;

    /** 订单交易类型 */
    private OrderTradeTypeVO orderTradeType = OrderTradeTypeVO.rebate_no_pay_trade;

}
