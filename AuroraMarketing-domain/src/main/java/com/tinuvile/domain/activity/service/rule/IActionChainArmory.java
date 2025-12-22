package com.tinuvile.domain.activity.service.rule;


/**
 * @author Tinuvile
 * @description 下单规则过滤接口
 * @since 2025/12/22
 */
public interface IActionChainArmory {

    IActionChain next();

    IActionChain appendNext(IActionChain next);

}
