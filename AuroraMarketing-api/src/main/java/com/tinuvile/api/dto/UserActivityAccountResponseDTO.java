package com.tinuvile.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description 用户活动账户响应对象
 * @since 2026/1/21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserActivityAccountResponseDTO {

    /** 总次数 */
    private Integer totalCount;

    /** 总次数-剩余 */
    private Integer totalCountSurplus;

    /** 日次数 */
    private Integer dayCount;

    /** 日次数-剩余 */
    private Integer dayCountSurplus;

    /** 月次数 */
    private Integer monthCount;

    /** 月次数-剩余*/
    private Integer monthCountSurplus;

}
