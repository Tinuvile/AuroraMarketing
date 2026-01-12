package com.tinuvile.api;


import com.tinuvile.api.dto.ActivityDrawRequestDTO;
import com.tinuvile.api.dto.ActivityDrawResponseDTO;
import com.tinuvile.types.model.Response;

/**
 * @author Tinuvile
 * @description 抽奖活动服务
 * @since 2026/1/12
 */
public interface IRaffleActivityService {

    /**
     * @description 活动装配，数据预热缓存
     * @param activityId 活动ID
     * @return 装配结果
     */
    Response<Boolean> armory(Long activityId);

    /**
     * @description 活动抽奖接口
     * @param request 抽奖请求对象
     * @return 抽奖结果
     */
    Response<ActivityDrawResponseDTO> draw(ActivityDrawRequestDTO request);

}
