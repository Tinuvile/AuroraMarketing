package com.tinuvile.test.domain.strategy;


import com.alibaba.fastjson.JSON;
import com.tinuvile.domain.strategy.model.valobj.*;
import com.tinuvile.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.tinuvile.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Tinuvile
 * @description 规则树测试
 * @since 2025/11/30
 */
@Ignore
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogicTreeTest {

    @Resource
    private DefaultTreeFactory defaultTreeFactory;

    @Test
    public void test_tree_lock() {
        RuleTreeNodeVO rule_lock = RuleTreeNodeVO.builder()
                .treeId("tree_lock")
                .ruleKey("rule_lock")
                .ruleDesc("限定用户已完成N次抽奖后解锁")
                .ruleValue("101:200, 10000")
                .treeNodeLineVOList(new ArrayList<RuleTreeNodeLineVO>() {{
                    add(RuleTreeNodeLineVO.builder()
                            .treeId("tree_lock")
                            .ruleNodeFrom("rule_lock")
                            .ruleNodeTo("rule_stock")
                            .ruleLimitType(RuleLimitTypeVO.EQUAL)
                            .ruleLimitValue(RuleLogicCheckTypeVO.ALLOW)
                            .build());
                    add(RuleTreeNodeLineVO.builder()
                            .treeId("tree_lock")
                            .ruleNodeFrom("rule_lock")
                            .ruleNodeTo("rule_luck_award")
                            .ruleLimitType(RuleLimitTypeVO.EQUAL)
                            .ruleLimitValue(RuleLogicCheckTypeVO.TAKE_OVER)
                            .build());
                }})
                .build();

        RuleTreeNodeVO rule_luck_award = RuleTreeNodeVO.builder()
                .treeId("tree_lock")
                .ruleKey("rule_luck_award")
                .ruleDesc("兜底奖品随机token")
                .ruleValue("101:200,10000")
                .treeNodeLineVOList(new ArrayList<RuleTreeNodeLineVO>() {{
                    add(RuleTreeNodeLineVO.builder()
                            .treeId("tree_lock")
                            .ruleNodeFrom("rule_luck_award")
                            .ruleNodeTo("rule_stock")
                            .ruleLimitType(RuleLimitTypeVO.EQUAL)
                            .ruleLimitValue(RuleLogicCheckTypeVO.TAKE_OVER)
                            .build());
                }})
                .build();

        RuleTreeNodeVO rule_stock = RuleTreeNodeVO.builder()
                .treeId("tree_lock")
                .ruleKey("rule_stock")
                .ruleDesc("库存扣减规则")
                .ruleValue("101:200,10000")
                .ruleValue(null)
                .treeNodeLineVOList(null)
                .build();

        RuleTreeVO ruleTreeVO = new RuleTreeVO();
        ruleTreeVO.setTreeId("tree_lock");
        ruleTreeVO.setTreeName("规则树");
        ruleTreeVO.setTreeDesc("规则树");
        ruleTreeVO.setTreeRootRuleNode("rule_lock");

        ruleTreeVO.setTreeNodeMap(new HashMap<String, RuleTreeNodeVO>() {{
            put("rule_lock", rule_lock);
            put("rule_stock", rule_stock);
            put("rule_luck_award", rule_luck_award);
        }});

        IDecisionTreeEngine treeEngine = defaultTreeFactory.openLogicTree(ruleTreeVO);

        DefaultTreeFactory.StrategyAwardVO strategyAwardVO = treeEngine.process("Tinuvile", 10001L, 105, new Date());
        log.info("测试结果：{}", JSON.toJSONString(strategyAwardVO));
    }

}
