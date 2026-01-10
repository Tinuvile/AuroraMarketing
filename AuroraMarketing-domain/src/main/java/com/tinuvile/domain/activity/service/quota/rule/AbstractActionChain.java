package com.tinuvile.domain.activity.service.quota.rule;


/**
 * @author Tinuvile
 * @description 下单规则责任链抽象类
 * @since 2025/12/22
 */
public abstract class AbstractActionChain implements IActionChain {

    private IActionChain next;

    @Override
    public IActionChain next() {
        return next;
    }

    @Override
    public IActionChain appendNext(IActionChain next) {
        this.next = next;
        return next;
    }

}
