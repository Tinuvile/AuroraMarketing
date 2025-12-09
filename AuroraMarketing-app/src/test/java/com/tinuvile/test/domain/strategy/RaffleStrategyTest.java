package com.tinuvile.test.domain.strategy;


import com.alibaba.fastjson.JSON;
import com.tinuvile.domain.strategy.model.entity.RaffleAwardEntity;
import com.tinuvile.domain.strategy.model.entity.RaffleFactorEntity;
import com.tinuvile.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import com.tinuvile.domain.strategy.service.IRaffleStock;
import com.tinuvile.domain.strategy.service.IRaffleStrategy;
import com.tinuvile.domain.strategy.service.armory.IStrategyArmory;
import com.tinuvile.domain.strategy.service.rule.chain.impl.RuleWeightLogicChain;
import com.tinuvile.domain.strategy.service.rule.tree.impl.RuleLockLogicTreeNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    private RuleLockLogicTreeNode ruleLockLogicTreeNode;
    @Resource
    private IRaffleStock raffleStock;

    @Before
    public void setUp() {
        boolean success1 = strategyArmory.assembleLotteryStrategy(10001L);
        log.info("10001策略装配初始化结果:{}", success1);

        boolean success2 = strategyArmory.assembleLotteryStrategy(10002L);
        log.info("10002策略装配初始化结果:{}", success2);

        boolean success3 = strategyArmory.assembleLotteryStrategy(10003L);
        log.info("10003策略装配初始化结果:{}", success3);

        // 设置用户权重分数
        ReflectionTestUtils.setField(ruleWeightLogicChain, "userScore", 40500L);
        ReflectionTestUtils.setField(ruleLockLogicTreeNode, "userRaffleCount", 10L);
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

    @Test
    public void test_raffle_stock() throws InterruptedException {

        log.info("开始完整库存扣减流程测试");

        List<RaffleAwardEntity> raffleResults = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            try {
                RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                        .userId("Tinuvile")
                        .strategyId(10003L)
                        .build();

                RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);
                raffleResults.add(raffleAwardEntity);

                log.info("第{}次抽奖 - 请求参数：{}", i + 1, JSON.toJSONString(raffleFactorEntity));
                log.info("第{}次抽奖 - 响应结果：{}", i + 1, JSON.toJSONString(raffleAwardEntity));

                // new CountDownLatch(1).await();

                Assert.assertNotNull("抽奖结果不能为空", raffleAwardEntity);
                Assert.assertNotNull("奖品ID不能为空", raffleAwardEntity.getAwardId());

            } catch (Exception e) {
                log.error("库存测试执行出现异常: {}",e.getMessage(), e);
                throw e;
            }
        }

        log.info("等待延迟队列数据可消费");
        Thread.sleep(4000);

        log.info("检查队列中的库存更新任务");
        List<StrategyAwardStockKeyVO> queueItems = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            StrategyAwardStockKeyVO strategyAwardStockKeyVO = raffleStock.takeQueueValue();
            if (strategyAwardStockKeyVO != null) {
                queueItems.add(strategyAwardStockKeyVO);
                log.info("队列任务{}: {}", i+1, JSON.toJSONString(strategyAwardStockKeyVO));

                log.info("执行库存更新 - strategyId:{} awardId:{}",
                        strategyAwardStockKeyVO.getStrategyId(),
                        strategyAwardStockKeyVO.getAwardId());
                raffleStock.updateStrategyAwardStock(
                        strategyAwardStockKeyVO.getStrategyId(),
                        strategyAwardStockKeyVO.getAwardId());
            }
        }

        log.info("验证测试结果");
        Assert.assertEquals("应该有3次抽奖结果", 3, raffleResults.size());
        Assert.assertEquals("队列中应该有3个库存更新任务", 3, queueItems.size());

    }

}
