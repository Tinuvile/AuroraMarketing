package com.tinuvile.domain.rebate.model.entity;


import com.tinuvile.domain.rebate.model.valobj.BehaviorTypeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description
 * @since 2026/1/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BehaviorEntity {

    /** 用户ID */
    private String userId;

    /** 行为类型（sign 签到、openai_pay 支付） */
    private BehaviorTypeVO behaviorTypeVO;

    /** 业务ID - 签到则是日期字符串，支付则是外部的业务ID */
    private String outBusinessNo;

}
