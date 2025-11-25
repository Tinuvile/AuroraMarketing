package com.tinuvile.test.domain;


import com.alibaba.fastjson.JSON;
import com.tinuvile.domain.strategy.model.entity.RaffleAwardEntity;
import com.tinuvile.domain.strategy.model.entity.RaffleFactorEntity;
import com.tinuvile.domain.strategy.service.IRaffleStrategy;
import com.tinuvile.domain.strategy.service.rule.impl.RuleWeightLogicFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

/**
 * @author Tinuvile
 * @description 抽奖策略测试类
 * @since 2025/11/25
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleStrategyTest {

    @Resource
    private IRaffleStrategy raffleStrategy;
    @Resource
    private RuleWeightLogicFilter ruleWeightLogicFilter;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(ruleWeightLogicFilter, "userScore", 40500L);
    }

    @Test
    public void test_performRaffle() {
        try {
            RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                    .userId("Tinuvile")
                    .strategyId(10001L)
                    .build();

            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

            log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
            log.info("响应结果：{}", JSON.toJSONString(raffleAwardEntity));

            Assert.assertNotNull("抽奖结果不能为空", raffleAwardEntity);
            Assert.assertNotNull("奖品ID不能为空", raffleAwardEntity.getAwardId());

        } catch (Exception e) {
            log.error("测试执行出现异常: {}",e.getMessage(), e);
            throw e;
        }
    }

    @Test
    public void test_performRaffle_blacklist() {
        try {
            RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                    .userId("user001")
                    .strategyId(10001L)
                    .build();

            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

            log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
            log.info("响应结果：{}", JSON.toJSONString(raffleAwardEntity));

            Assert.assertNotNull("抽奖结果不能为空", raffleAwardEntity);
            Assert.assertEquals("黑名单用户应该获得固定奖品", Integer.valueOf(100), raffleAwardEntity.getAwardId());

        } catch (Exception e) {
            log.error("黑名单测试执行出现异常: {}",e.getMessage(), e);
            throw e;
        }
    }

}
