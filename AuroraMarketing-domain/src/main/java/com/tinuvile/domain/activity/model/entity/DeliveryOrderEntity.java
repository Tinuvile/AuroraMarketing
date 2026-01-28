package com.tinuvile.domain.activity.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description 出货单实体对象
 * @since 2026/1/28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryOrderEntity {

    /** 用户ID */
    private String userId;

    /** 业务防重单号 */
    private String outBusinessNo;

}
