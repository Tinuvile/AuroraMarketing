package com.tinuvile.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description 抽奖响应参数
 * @since 2025/12/4
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleStrategyResponseDTO {

    /** 奖品ID */
    private Integer awardId;

    /** 排序编号【策略奖品配置的奖品顺序编号】 */
    private Integer awardIndex;

}
