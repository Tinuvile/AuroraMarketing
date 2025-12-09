package com.tinuvile.test.domain.activity;


import com.alibaba.fastjson.JSON;
import com.tinuvile.domain.activity.model.entity.ActivityOrderEntity;
import com.tinuvile.domain.activity.model.entity.ActivityShopCartEntity;
import com.tinuvile.domain.activity.service.IRaffleOrder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Tinuvile
 * @description 抽奖活动订单单测
 * @since 2025/12/9
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleOrderTest {

    @Resource
    private IRaffleOrder raffleOrder;

    @Test
    public void test_createRaffleActivityOrder() {
        ActivityShopCartEntity activityShopCartEntity = new ActivityShopCartEntity();
        activityShopCartEntity.setUserId("Tinuvile");
        activityShopCartEntity.setSku(9011L);
        ActivityOrderEntity raffleActivityOrder = raffleOrder.createRaffleActivityOrder(activityShopCartEntity);
        log.info("测试结果：{}", JSON.toJSONString(raffleActivityOrder));
    }

}
