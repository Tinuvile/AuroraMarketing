package com.tinuvile.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description 抽奖奖品列表响应参数
 * @since 2025/12/4
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardListResponseDTO {

    /** 奖品ID */
    private Integer awardId;

    /** 奖品名称 */
    private String awardTitle;

    /** 奖品副标题 */
    private String awardSubtitle;

    /** 排序编号 */
    private Integer sort;

    /** 奖品次数规则 */
    private Integer awardRuleLockCount;

    /** 奖品是否解锁 */
    private Boolean isAwardUnlock;

    /** 等待解锁数量 */
    private Integer waitUnlockCount;

}
