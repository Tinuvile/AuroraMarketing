package com.tinuvile.api;


import com.tinuvile.api.dto.RaffleAwardListRequestDTO;
import com.tinuvile.api.dto.RaffleAwardListResponseDTO;
import com.tinuvile.api.dto.RaffleRequestDTO;
import com.tinuvile.api.dto.RaffleResponseDTO;
import com.tinuvile.api.response.Response;

import java.util.List;

/**
 * @author Tinuvile
 * @description 抽奖服务接口
 * @since 2025/12/4
 */
public interface IRaffleService {

    /**
     * 策略装配接口
     * @param strategyId 策略ID
     * @return 装配结果
     */
    Response<Boolean> strategyArmory(Long strategyId);

    /**
     * 查询抽奖奖品列表接口
     * @param requestDTO 请求参数
     * @return 奖品列表
     */
    Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(RaffleAwardListRequestDTO requestDTO);

    /**
     * 随机抽奖接口
     * @param requestDTO 请求参数
     * @return 抽奖结果
     */
    Response<RaffleResponseDTO> randomRaffle(RaffleRequestDTO requestDTO);

}
