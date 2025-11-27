package com.tinuvile.domain.strategy.service.rule.chain;


import lombok.extern.slf4j.Slf4j;

/**
 * @author Tinuvile
 * @description 抽奖模式责任链，判断走哪种抽奖模式。比如：默认、权重、黑名单
 * @since 2025/11/27
 */
@Slf4j
public abstract class AbstractLogicChain implements ILogicChain {

    private ILogicChain next;

    @Override
    public ILogicChain next() {
        return next;
    }

    @Override
    public ILogicChain appendNext(ILogicChain next) {
        this.next = next;
        return next;
    }

    protected abstract String ruleModel();

}
