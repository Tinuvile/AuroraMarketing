package com.tinuvile.domain.strategy.service;


import com.tinuvile.domain.strategy.model.entity.RaffleAwardEntity;
import com.tinuvile.domain.strategy.model.entity.RaffleFactorEntity;
import com.tinuvile.domain.strategy.model.entity.RuleActionEntity;
import com.tinuvile.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.tinuvile.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import com.tinuvile.domain.strategy.repository.IStrategyRepository;
import com.tinuvile.domain.strategy.service.armory.IStrategyDispatch;
import com.tinuvile.domain.strategy.service.rule.chain.ILogicChain;
import com.tinuvile.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.tinuvile.types.enums.ResponseCode;
import com.tinuvile.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Tinuvile
 * @description 抽奖策略抽象类，定义抽奖的标准流程
 * @since 2025/11/25
 */
@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {

    protected IStrategyRepository repository;
    protected IStrategyDispatch strategyDispatch;
    private final DefaultChainFactory defaultChainFactory;

    public AbstractRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch, DefaultChainFactory defaultChainFactory) {
        this.repository = repository;
        this.strategyDispatch = strategyDispatch;
        this.defaultChainFactory = defaultChainFactory;
    }

    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {

        // 参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if (null == strategyId || StringUtils.isBlank(userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 获取抽奖责任链 - 前置规则责任链处理
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);

        // 责任链获取奖品ID
        Integer awardId = logicChain.logic(userId, strategyId);

        // 查询奖品规则【抽奖中 - 根据奖品ID过滤、抽奖后 - 扣减奖品库存后过滤和判断兜底】
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModelVO(strategyId, awardId);

        // 抽奖中置规则过滤
        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> ruleActionCenterEntity = this.doCheckRaffleCenterLogic(
                RaffleFactorEntity.builder()
                        .userId(userId)
                        .strategyId(strategyId)
                        .awardId(awardId)
                        .build(), strategyAwardRuleModelVO.raffleCenterRuleModelList()
        );

        if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionCenterEntity.getCode())) {
            log.info("中奖中置规则拦截，通过抽奖后规则 rule_luck_award 走兜底路径");
            return RaffleAwardEntity.builder()
                    .awardDesc("中奖中置规则拦截，通过抽奖后规则 rule_luck_award 走兜底路径")
                    .build();
        }

        return RaffleAwardEntity.builder()
                .awardId(awardId)
                .build();

    }

    protected abstract RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logics);

}
