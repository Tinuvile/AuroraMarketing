package com.tinuvile.domain.activity.service.quota.rule.factory;


import com.tinuvile.domain.activity.service.quota.rule.IActionChain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Tinuvile
 * @description 责任链工厂
 * @since 2025/12/22
 */
@Service
public class DefaultActivityChainFactory {

    private final IActionChain actionChain;

    /**
     * 通过构造函数注入，活动下单动作的责任链是固定的，所以直接在构造函数中组装
     */
    public DefaultActivityChainFactory(Map<String, IActionChain> actionChainGroup) {
        actionChain = actionChainGroup.get(ActionModel.activity_base_action.code);
        actionChain.appendNext(actionChainGroup.get(ActionModel.activity_sku_stock_action.code));
    }

    public IActionChain openActionChain() {
        return this.actionChain;
    }

    @Getter
    @AllArgsConstructor
    public enum ActionModel {
        activity_base_action("activity_base_action", "活动的库存、时间校验"),
        activity_sku_stock_action("activity_sku_stock_action", "活动sku库存校验"),
        ;

        private final String code;
        private final String info;
    }

}
