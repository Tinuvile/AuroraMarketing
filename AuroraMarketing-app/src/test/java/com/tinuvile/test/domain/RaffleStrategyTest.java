package com.tinuvile.test.domain;


import com.alibaba.fastjson.JSON;
import com.tinuvile.domain.strategy.model.entity.RaffleAwardEntity;
import com.tinuvile.domain.strategy.model.entity.RaffleFactorEntity;
import com.tinuvile.domain.strategy.service.IRaffleStrategy;
import com.tinuvile.domain.strategy.service.armory.IStrategyArmory;
import com.tinuvile.domain.strategy.service.rule.chain.impl.RuleWeightLogicChain;
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
    private RuleWeightLogicChain ruleWeightLogicChain;
    @Resource
    private IStrategyArmory strategyArmory;

    @Before
    public void setUp() {
        boolean success1 = strategyArmory.assembleLotteryStrategy(10001L);
        log.info("10001策略装配初始化结果:{}", success1);

        boolean success2 = strategyArmory.assembleLotteryStrategy(10002L);
        log.info("10002策略装配初始化结果:{}", success2);

        // 设置用户权重分数
        ReflectionTestUtils.setField(ruleWeightLogicChain, "userScore", 40500L);
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

    @Test
    public void test_raffle_center_rule_lock() {
        try {
            // 测试规则树锁定逻辑
            ReflectionTestUtils.setField(ruleWeightLogicChain, "userScore", 10L);

            RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                    .userId("Tinuvile")
                    .strategyId(10002L)
                    .build();

            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

            log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
            log.info("响应结果：{}", JSON.toJSONString(raffleAwardEntity));

            Assert.assertNotNull("抽奖结果不能为空", raffleAwardEntity);
            
            // 验证规则树处理后的奖品ID
            log.info("规则树处理结果 - 奖品ID: {}", raffleAwardEntity.getAwardId());

        }  catch (Exception e) {
            log.error("规则树锁定测试执行出现异常: {}",e.getMessage(), e);
            throw e;
        }
    }

}
