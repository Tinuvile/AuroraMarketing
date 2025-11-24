package com.tinuvile.domain.strategy.model.entity;


import com.tinuvile.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tinuvile
 * @description 策略规则实体
 * @since 2025/11/24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyRuleEntity {

    /** 策略ID */
    private Long strategyId;

    /** 奖品ID */
    private Integer awardId;

    /** 抽象规则类型；1-策略规则、2-奖品规则 */
    private Integer ruleType;

    /** 抽奖规则类型 */
    private String ruleModel;

    /** 抽奖规则比值 */
    private String ruleValue;

    /** 抽奖规则描述 */
    private String ruleDesc;

    /**
     * 获取权重值
     * 数据案例：
     * 30:103,104,105,106 100:105,106,107,108
     */
    public Map<String, List<Integer>> getRuleWeightValues() {
        if (!"rule_weight".equals(ruleModel)) return null;
        String[] ruleValueGroups = ruleValue.split(Constants.SPACE);
        Map<String, List<Integer>> resultMap = new HashMap<>();
        for (String ruleValueGroup : ruleValueGroups) {
            if (ruleValueGroup == null || ruleValueGroup.isEmpty()) return resultMap;
            String[] parts = ruleValueGroup.split(Constants.COLON);
            if (parts.length != 2) throw new IllegalArgumentException("非法权重值格式：" + ruleValueGroup);
            String[] valueStrings = parts[1].split(Constants.SPLIT);
            List<Integer> values = new ArrayList<>();
            for (String valueString : valueStrings) {
                values.add(Integer.parseInt(valueString));
            }
            resultMap.put(ruleValueGroup, values);
        }
        return resultMap;
    }

}
