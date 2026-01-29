package com.tinuvile.api;


import com.tinuvile.api.dto.*;
import com.tinuvile.types.model.Response;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Tinuvile
 * @description 抽奖活动服务
 * @since 2026/1/12
 */
public interface IRaffleActivityService {

    /**
     * @description 活动装配，数据预热缓存
     *
     * @param activityId 活动ID
     * @return 装配结果
     */
    Response<Boolean> armory(Long activityId);

    /**
     * @description 活动抽奖接口
     *
     * @param request 抽奖请求对象
     * @return 抽奖结果
     */
    Response<ActivityDrawResponseDTO> draw(ActivityDrawRequestDTO request);

    /**
     * @description 日历签到返利接口
     *
     * @param userId 用户ID
     * @return 签到结果
     */
    Response<Boolean> calendarSignRebate(@RequestParam String userId);

    /**
     * @description 判断是否完成日历签到返利接口
     *
     * @param userId 用户ID
     * @return 是否已签到返利
     */
    Response<Boolean> isCalendarSignRebate(String userId);

    /**
     * @description 查询用户活动账户接口
     *
     * @param request 查询请求对象
     * @return 用户活动账户信息
     */
    Response<UserActivityAccountResponseDTO> queryUserActivityAccount(UserActivityAccountRequestDTO request);

    /**
     * @description 查询sku商品集合
     *
     * @param activityId 活动ID
     * @return 商品集合
     */
    Response<List<SkuProductResponseDTO>> querySkuProductListByActivityId(Long activityId);

    /**
     * @description 查询用户积分值
     *
     * @param userId 用户ID
     * @return 用户积分值
     */
    Response<BigDecimal> queryUserCreditAccount(String userId);

    /**
     * @description 积分支付兑换商品
     *
     * @param request 请求对象【用户ID、商品ID】
     * @return 兑换结果
     */
    Response<Boolean> creditPayExchangeSku(SkuProductShopCartRequestDTO request);

}
