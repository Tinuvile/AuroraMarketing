package com.tinuvile.trigger.http;


import com.tinuvile.api.IRaffleActivityService;
import com.tinuvile.api.dto.ActivityDrawRequestDTO;
import com.tinuvile.api.dto.ActivityDrawResponseDTO;
import com.tinuvile.domain.activity.model.entity.UserRaffleOrderEntity;
import com.tinuvile.domain.activity.service.IRaffleActivityPartakeService;
import com.tinuvile.domain.activity.service.armory.IActivityArmory;
import com.tinuvile.domain.award.model.entity.UserAwardRecordEntity;
import com.tinuvile.domain.award.model.valobj.AwardStateVO;
import com.tinuvile.domain.award.service.IAwardService;
import com.tinuvile.domain.strategy.model.entity.RaffleAwardEntity;
import com.tinuvile.domain.strategy.model.entity.RaffleFactorEntity;
import com.tinuvile.domain.strategy.service.IRaffleStrategy;
import com.tinuvile.domain.strategy.service.armory.IStrategyArmory;
import com.tinuvile.types.enums.ResponseCode;
import com.tinuvile.types.exception.AppException;
import com.tinuvile.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Tinuvile
 * @description 抽奖活动服务
 * @since 2026/1/12
 */
@Slf4j
@RestController
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/raffle/activity/")
public class RaffleActivityController implements IRaffleActivityService {

    @Resource
    private IRaffleActivityPartakeService raffleActivityPartakeService;
    @Resource
    private IRaffleStrategy raffleStrategy;
    @Resource
    private IAwardService awardService;
    @Resource
    private IActivityArmory activityArmory;
    @Resource
    private IStrategyArmory strategyArmory;


    /**
     * 活动装配 - 数据预热 | 把活动配置的对应sku一起装配
     *
     * @param activityId 活动ID
     * @return 是否装配成功
     *
     * <p>
     *     接口：<a href="http://localhost:8091/api/v1/raffle/activity/armory">/api/v1/raffle/activity/armory</a>
     *     入参：{"activityId":100001,"userId":"tinuvile"}
     * </p>
     *
     * curl --request GET \
     *   -- url 'http://localhost:8091/api/v1/raffle/activity/armory?activityId=100301'
     *
     */
    @RequestMapping(value = "armory", method = RequestMethod.GET)
    @Override
    public Response<Boolean> armory(Long activityId) {
        try {
            log.info("活动装配：数据预热，开始 activityId:{}", activityId);
            // 活动装配
            activityArmory.assembleActivitySkuByActivityId(activityId);
            // 策略装配
            strategyArmory.assembleLotteryStrategyByActivityId(activityId);
            Response<Boolean> response = Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(true)
                    .build();
            log.info("活动装配，数据预热，完成 activityId:{}", activityId);
            return response;
        } catch (Exception e) {
            log.error("活动装配，数据预热，失败 activityId:{}", activityId, e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .data(false)
                    .build();
        }
    }

    /**
     * 抽奖接口
     *
     * @param request 抽奖请求对象
     * @return 抽奖结果
     *
     * <p>
     *     接口：<a href="http://localhost:8091/api/v1/raffle/activity/draw">/api/v1/raffle/activity/draw</a>
     *     入参：{"activityId":100001,"userId":"tinuvile"}
     * </p>
     *
     * curl --request POST \
     *   -- url 'http://localhost:8091/api/v1/raffle/activity/draw' \
     *   --header 'Content-Type: application/json' \
     *   --data '{"activityId":100301,"userId":"tinuvile"}'
     *
     */
    @RequestMapping(value = "draw", method = RequestMethod.POST)
    @Override
    public Response<ActivityDrawResponseDTO> draw(ActivityDrawRequestDTO request) {
        try {
            log.info("活动抽奖 userId:{} activityId:{}", request.getUserId(), request.getActivityId());

            // 参数校验
            if (StringUtils.isBlank(request.getUserId()) || null == request.getActivityId()) {
                throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
            }

            // 参与活动 - 创建参与记录订单
            UserRaffleOrderEntity orderEntity = raffleActivityPartakeService.createOrder(request.getUserId(), request.getActivityId());
            log.info("活动抽奖，创建订单 userId:{} activityId:{} orderId:{}", request.getUserId(), request.getActivityId(), orderEntity.getOrderId());

            // 抽奖策略，执行抽奖
            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(
                    RaffleFactorEntity.builder()
                    .userId(orderEntity.getUserId())
                    .strategyId(orderEntity.getStrategyId())
                    .build()
            );

            // 存放结果 - 写入中奖记录
            UserAwardRecordEntity userAwardRecord = UserAwardRecordEntity.builder()
                    .userId(orderEntity.getUserId())
                    .activityId(orderEntity.getActivityId())
                    .strategyId(orderEntity.getStrategyId())
                    .orderId(orderEntity.getOrderId())
                    .awardId(raffleAwardEntity.getAwardId())
                    .awardTitle(raffleAwardEntity.getAwardTitle())
                    .awardTime(new Date())
                    .awardState(AwardStateVO.create)
                    .build();
            awardService.saveUserAwardRecord(userAwardRecord);

            // 返回结果
            return Response.<ActivityDrawResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(ActivityDrawResponseDTO.builder()
                            .awardId(raffleAwardEntity.getAwardId())
                            .awardTitle(raffleAwardEntity.getAwardTitle())
                            .awardIndex(raffleAwardEntity.getSort())
                            .build())
                    .build();

        } catch (AppException e) {
            log.error("活动抽奖失败 userId:{} activityId:{}", request.getUserId(), request.getActivityId(), e);
            return Response.<ActivityDrawResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("活动抽奖失败 userId:{} activityId:{}", request.getUserId(), request.getActivityId(), e);
            return Response.<ActivityDrawResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();

        }
    }
}
