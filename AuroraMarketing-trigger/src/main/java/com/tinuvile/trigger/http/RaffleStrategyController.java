package com.tinuvile.trigger.http;


import com.alibaba.fastjson.JSON;
import com.tinuvile.api.IRaffleStrategyService;
import com.tinuvile.api.dto.RaffleAwardListRequestDTO;
import com.tinuvile.api.dto.RaffleAwardListResponseDTO;
import com.tinuvile.api.dto.RaffleStrategyRequestDTO;
import com.tinuvile.api.dto.RaffleStrategyResponseDTO;
import com.tinuvile.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.tinuvile.domain.strategy.model.entity.RaffleAwardEntity;
import com.tinuvile.domain.strategy.model.entity.RaffleFactorEntity;
import com.tinuvile.domain.strategy.model.entity.StrategyAwardEntity;
import com.tinuvile.domain.strategy.service.IRaffleAward;
import com.tinuvile.domain.strategy.service.IRaffleRule;
import com.tinuvile.domain.strategy.service.IRaffleStrategy;
import com.tinuvile.domain.strategy.service.armory.IStrategyArmory;
import com.tinuvile.types.enums.ResponseCode;
import com.tinuvile.types.exception.AppException;
import com.tinuvile.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Tinuvile
 * @description 营销抽奖服务
 * @since 2025/12/4
 */
@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/raffle/strategy")
public class RaffleStrategyController implements IRaffleStrategyService {

    @Resource
    private IRaffleAward raffleAward;
    @Resource
    private IRaffleRule raffleRule;
    @Resource
    private IRaffleStrategy raffleStrategy;
    @Resource
    private IStrategyArmory strategyArmory;
    @Resource
    private IRaffleActivityAccountQuotaService raffleActivityAccountQuotaService;

    /**
     * 策略装配
     * <a href="https://localhost:8091/api/v1/raffle/strategy/strategy_armory">/api/v1/raffle/strategy/strategy_armory</a>
     *
     * @param strategyId 策略ID
     * @return 装配结果
     */
    @RequestMapping(value = "strategy_armory", method = RequestMethod.GET)
    @Override
    public Response<Boolean> strategyArmory(@RequestParam Long strategyId) {
        try {
            log.info("抽奖策略装配开始 strategyId:{}", strategyId);

            boolean armoryStatus = strategyArmory.assembleLotteryStrategy(strategyId);
            Response<Boolean> response = Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(armoryStatus)
                    .build();

            log.info("抽奖策略装配完成 strategyId:{} response:{}", strategyId, JSON.toJSONString(response));
            return response;
        } catch (Exception e) {
            log.error("抽奖策略装配失败 strategyId:{}", strategyId, e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 查询抽奖奖品列表
     * <a href="https://localhost:8091/api/v1/raffle/strategy/query_raffle_award_list">/api/v1/raffle/strategy/query_raffle_award_list</a>
     *
     * @param request {"activityId":100301,"userId":"tinuvile"}
     * @return 奖品列表
     */
    @RequestMapping(value = "query_raffle_award_list", method = RequestMethod.POST)
    @Override
    public Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(@RequestBody RaffleAwardListRequestDTO request) {
        try {
            log.info("查询抽奖奖品列表开始 userId:{} activityId:{}", request.getUserId(), request.getActivityId());

            // 查询奖品配置
            List<StrategyAwardEntity> strategyAwardEntities = raffleAward.queryRaffleStrategyAwardListByActivityId(request.getActivityId());
            // 获取规则配置
            String[] treeIds = strategyAwardEntities.stream()
                    .map(StrategyAwardEntity::getRuleModels)
                    .filter(ruleModel -> ruleModel != null && !ruleModel.isEmpty())
                    .toArray(String[]::new);
            // 查询规则配置 - 获取奖品的解锁限制
            Map<String, Integer> ruleLockCountMap = raffleRule.queryAwardRuleLockCount(treeIds);
            // 查询用户抽奖次数
            Integer dayPartakeCount = raffleActivityAccountQuotaService.queryRaffleActivityAccountDayPartakeCount(request.getActivityId(), request.getUserId());
            // 遍历填充数据
            List<RaffleAwardListResponseDTO> raffleAwardListResponseDTOS = new ArrayList<>(strategyAwardEntities.size());
            for (StrategyAwardEntity strategyAwardEntity : strategyAwardEntities) {
                Integer awardRuleLockCount = ruleLockCountMap.get(strategyAwardEntity.getRuleModels());
                raffleAwardListResponseDTOS.add(
                        RaffleAwardListResponseDTO.builder()
                                .awardId(strategyAwardEntity.getAwardId())
                                .awardTitle(strategyAwardEntity.getAwardTitle())
                                .awardSubtitle(strategyAwardEntity.getAwardSubtitle())
                                .sort(strategyAwardEntity.getSort())
                                .awardRuleLockCount(awardRuleLockCount)
                                .isAwardUnlock(null == awardRuleLockCount || dayPartakeCount >= awardRuleLockCount)
                                .waitUnLockCount(null == awardRuleLockCount || dayPartakeCount >= awardRuleLockCount ? 0 : awardRuleLockCount - dayPartakeCount)
                                .build()
                );
            }
            Response<List<RaffleAwardListResponseDTO>> response = Response.<List<RaffleAwardListResponseDTO>>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(raffleAwardListResponseDTOS)
                    .build();

            log.info("查询抽奖奖品列表配置完成 userId:{} activityId:{} response:{}", request.getUserId(), request.getActivityId(), JSON.toJSONString(response));
            return response;
        } catch (Exception e) {
            log.error("查询抽奖奖品列表配置失败 userId:{} activityId:{}", request.getUserId(), request.getActivityId(), e);
            return Response.<List<RaffleAwardListResponseDTO>>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 随机抽奖接口
     * <a href="https://localhost:8091/api/v1/raffle/strategy/random_raffle">/api/v1/raffle/strategy/random_raffle</a>
     *
     * @param requestDTO 请求参数
     * @return 抽奖结果
     */
    @RequestMapping(value = "random_raffle", method = RequestMethod.POST)
    @Override
    public Response<RaffleStrategyResponseDTO> randomRaffle(@RequestBody RaffleStrategyRequestDTO requestDTO) {
        try {
            log.info("随机抽奖开始 strategyId:{}", requestDTO.getStrategyId());

            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(
                    RaffleFactorEntity.builder()
                            .userId("system")
                            .strategyId(requestDTO.getStrategyId())
                            .build()
            );

            Response<RaffleStrategyResponseDTO> response = Response.<RaffleStrategyResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(
                            RaffleStrategyResponseDTO.builder()
                                    .awardId(raffleAwardEntity.getAwardId())
                                    .awardIndex(raffleAwardEntity.getSort())
                                    .build()
                    )
                    .build();

            log.info("随机抽奖完成 strategyId:{} response:{}", requestDTO.getStrategyId(), JSON.toJSONString(response));
            return response;
        } catch (AppException e) {
            log.error("随机抽奖失败 strategyId:{} code:{} info:{}", requestDTO.getStrategyId(), e.getCode(), e.getInfo());
            return Response.<RaffleStrategyResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("随机抽奖失败 strategyId:{}", requestDTO.getStrategyId(), e);
            return Response.<RaffleStrategyResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

}
