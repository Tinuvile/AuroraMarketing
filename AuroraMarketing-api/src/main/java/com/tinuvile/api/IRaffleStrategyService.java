package com.tinuvile.api;


import com.tinuvile.api.dto.*;
import com.tinuvile.types.model.Response;

import java.util.List;

/**
 * @author Tinuvile
 * @description 抽奖服务接口
 * @since 2025/12/4
 */
public interface IRaffleStrategyService {

    /**
     * 策略装配接口
     *
     * @param strategyId 策略ID
     * @return 装配结果
     */
    Response<Boolean> strategyArmory(Long strategyId);

    /**
     * 查询抽奖奖品列表接口
     *
     * @param request 请求参数
     * @return 奖品列表
     */
    Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(RaffleAwardListRequestDTO request);

    /**
     * 查询抽奖策略权重规则接口
     *
     * @param request 请求参数
     * @return 权重规则列表
     */
    Response<List<RaffleStrategyRuleWeightResponseDTO>> queryRaffleStrategyRuleWeight(RaffleStrategyRuleWeightRequestDTO request);

    /**
     * 随机抽奖接口
     *
     * @param request 请求参数
     * @return 抽奖结果
     */
    Response<RaffleStrategyResponseDTO> randomRaffle(RaffleStrategyRequestDTO request);

}
