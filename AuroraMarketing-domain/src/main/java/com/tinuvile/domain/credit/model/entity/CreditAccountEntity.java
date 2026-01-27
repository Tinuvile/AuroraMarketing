package com.tinuvile.domain.credit.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Tinuvile
 * @description 积分账户实体
 * @since 2026/1/27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditAccountEntity {

    /** 用户ID */
    private String userId;

    /** 可用积分，每次扣减的值 */
    private BigDecimal adjustAmount;

}
