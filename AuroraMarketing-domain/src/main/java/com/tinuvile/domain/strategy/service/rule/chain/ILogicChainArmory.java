package com.tinuvile.domain.strategy.service.rule.chain;


/**
 * @author Tinuvile
 * @description 责任链装配
 * @since 2025/11/27
 */
public interface ILogicChainArmory {

    ILogicChain next();

    ILogicChain appendNext(ILogicChain next);

}
