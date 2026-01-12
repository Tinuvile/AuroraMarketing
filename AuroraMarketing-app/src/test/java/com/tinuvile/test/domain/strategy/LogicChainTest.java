package com.tinuvile.test.domain.strategy;


import com.alibaba.fastjson.JSON;
import com.tinuvile.domain.strategy.service.armory.IStrategyArmory;
import com.tinuvile.domain.strategy.service.rule.chain.ILogicChain;
import com.tinuvile.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.tinuvile.domain.strategy.service.rule.chain.impl.RuleWeightLogicChain;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

/**
 * @author Tinuvile
 * @description 抽奖责任链测试
 * @since 2025/12/1
 */
@Ignore
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogicChainTest {

    @Resource
    private IStrategyArmory strategyArmory;
    @Resource
    private RuleWeightLogicChain ruleWeightLogicChain;
    @Resource
    private DefaultChainFactory defaultChainFactory;

    @Before
    public void setUp() {
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(10001L));
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(10002L));
    }

    @Test
    public void test_LogicChain_rule_blacklist() {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(10001L);
        DefaultChainFactory.StrategyAwardVO strategyAwardVO = logicChain.logic("user001", 10001L);
        log.info("测试结果：{}", JSON.toJSONString(strategyAwardVO));
    }

    @Test
    public void test_LogicChain_rule_weight() {
        // 通过反射 mock 规则中的值
        ReflectionTestUtils.setField(ruleWeightLogicChain, "userScore", 4900L);

        ILogicChain logicChain = defaultChainFactory.openLogicChain(10001L);
        DefaultChainFactory.StrategyAwardVO strategyAwardVO = logicChain.logic("Tinuvile", 10001L);
        log.info("测试结果：{}", JSON.toJSONString(strategyAwardVO));
    }

    @Test
    public void test_LogicChain_rule_default() {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(10001L);
        DefaultChainFactory.StrategyAwardVO strategyAwardVO = logicChain.logic("Tinuvile", 10001L);
        log.info("测试结果：{}", JSON.toJSONString(strategyAwardVO));
    }

}
