package com.tinuvile.test.trigger;


import com.alibaba.fastjson.JSON;
import com.tinuvile.api.dto.ActivityDrawRequestDTO;
import com.tinuvile.api.dto.ActivityDrawResponseDTO;
import com.tinuvile.trigger.http.RaffleActivityController;
import com.tinuvile.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Tinuvile
 * @description 活动抽奖测试类
 * @since 2026/1/12
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityControllerTest {

    @Resource
    private RaffleActivityController raffleActivityController;

    /**
     * 测试活动装配接口
     * 测试数据：活动ID 100301，对应策略ID 10001
     */
    @Test
    public void test_armory() {
        Long activityId = 100301L;
        
        log.info("开始测试活动装配 - activityId: {}", activityId);
        
        Response<Boolean> response = raffleActivityController.armory(activityId);
        
        log.info("活动装配测试结果: {}", JSON.toJSONString(response));
        
        // 验证结果
        assert response != null;
        assert "0000".equals(response.getCode());
        assert response.getData() != null && response.getData();
        
        log.info("活动装配测试完成 - 装配成功: {}", response.getData());
    }

    /**
     * 测试活动抽奖接口
     * 测试数据：用户ID tinuvile，活动ID 100301
     */
    @Test
    public void test_draw() {
        // 先进行活动装配
        Long activityId = 100301L;
        String userId = "tinuvile";
        
        log.info("开始测试活动抽奖 - userId: {}, activityId: {}", userId, activityId);
        
        // 1. 先装配活动
        Response<Boolean> armoryResponse = raffleActivityController.armory(activityId);
        log.info("预装配活动结果: {}", JSON.toJSONString(armoryResponse));
        
        // 2. 执行抽奖
        ActivityDrawRequestDTO request = new ActivityDrawRequestDTO();
        request.setUserId(userId);
        request.setActivityId(activityId);
        
        log.info("抽奖请求参数: {}", JSON.toJSONString(request));
        
        Response<ActivityDrawResponseDTO> response = raffleActivityController.draw(request);
        
        log.info("活动抽奖测试结果: {}", JSON.toJSONString(response));
        
        // 验证结果
        assert response != null;
        assert "0000".equals(response.getCode());
        assert response.getData() != null;
        assert response.getData().getAwardId() != null;
        assert response.getData().getAwardTitle() != null;
        
        log.info("活动抽奖测试完成 - 中奖奖品ID: {}, 奖品名称: {}", 
                response.getData().getAwardId(), 
                response.getData().getAwardTitle());
    }
}