package com.tinuvile.test.domain.strategy;


import com.tinuvile.domain.strategy.service.armory.IStrategyArmory;
import com.tinuvile.domain.strategy.service.armory.IStrategyDispatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Tinuvile
 * @description 策略领域测试
 * @since 2025/11/23
 */
@Ignore
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StrategyTest {

    @Resource
    private IStrategyArmory strategyArmory;
    @Resource
    private IStrategyDispatch strategyDispatch;

    /**
     * 策略ID；10001L 装配的时候创建策略表写入到 Redis Map 中
     */
    @Test
    public void a_test_strategyArmory() {
        boolean success = strategyArmory.assembleLotteryStrategy(10001L);
        log.info("success = {}",success);
    }

    /**
     * 从装配的策略中随机获取奖品ID值
     */
    @Test
    public void b_test_getAssembleRandomVal() {
        log.info("测试结果：{} - 奖品ID值", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{} - 30次抽奖策略配置", strategyDispatch.getRandomAwardId(10001L, "30:103,104,105,106"));
    }

}
