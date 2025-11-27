package com.tinuvile.test.domain;


import com.alibaba.fastjson.JSON;
import com.tinuvile.domain.strategy.model.entity.RaffleAwardEntity;
import com.tinuvile.domain.strategy.model.entity.RaffleFactorEntity;
import com.tinuvile.domain.strategy.service.IRaffleStrategy;
import com.tinuvile.domain.strategy.service.armory.IStrategyArmory;
import com.tinuvile.domain.strategy.service.rule.filter.impl.RuleLockLogicFilter;
import com.tinuvile.domain.strategy.service.rule.chain.impl.RuleWeightLogicFilter;
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
    @Resource
    private IStrategyArmory strategyArmory;
    @Resource
    private RuleLockLogicFilter ruleLockLogicFilter;

    @Before
    public void setUp() {
        boolean success1 = strategyArmory.assembleLotteryStrategy(10001L);
        log.info("10001策略装配初始化结果:{}", success1);

        boolean success2 = strategyArmory.assembleLotteryStrategy(10002L);
        log.info("10002策略装配初始化结果:{}", success2);

        ReflectionTestUtils.setField(ruleWeightLogicFilter, "userScore", 40500L);
        ReflectionTestUtils.setField(ruleLockLogicFilter, "userRaffleCount", 10L);
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
            // 用户抽奖次数低于锁定次数
            ReflectionTestUtils.setField(ruleWeightLogicFilter, "userScore", 10L);
            ReflectionTestUtils.setField(ruleLockLogicFilter, "userRaffleCount", 0L);

            RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                    .userId("Tinuvile")
                    .strategyId(10002L)
                    .build();

            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

            log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
            log.info("响应结果：{}", JSON.toJSONString(raffleAwardEntity));

            Assert.assertNotNull("抽奖结果不能为空", raffleAwardEntity);

            // 用户抽奖次数超过锁定次数
            ReflectionTestUtils.setField(ruleLockLogicFilter, "userRaffleCount", 4L);

            raffleFactorEntity = RaffleFactorEntity.builder()
                    .userId("Tinuvile")
                    .strategyId(10002L)
                    .build();

            raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

            log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
            log.info("响应结果：{}", JSON.toJSONString(raffleAwardEntity));

            Assert.assertEquals("用户抽奖次数超过锁定次数，本场景下获得固定奖品", Integer.valueOf(105), raffleAwardEntity.getAwardId());

        }  catch (Exception e) {
            log.error("用户抽奖次数锁测试执行出现异常: {}",e.getMessage(), e);
            throw e;
        }
    }

}
