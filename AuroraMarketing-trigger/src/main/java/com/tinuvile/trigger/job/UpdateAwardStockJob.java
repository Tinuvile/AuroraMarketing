package com.tinuvile.trigger.job;


import com.tinuvile.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import com.tinuvile.domain.strategy.service.IRaffleStock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Tinuvile
 * @description 更新奖品库存任务
 * 使用redis更新缓存库存，异步队列更新数据库，避免压力打到数据库
 * @since 2025/12/2
 */
@Slf4j
@Component
public class UpdateAwardStockJob {

    @Resource
    private IRaffleStock raffleStock;

    @Scheduled(cron = "0/5 * * * * ?")
    public void exec() {
        try {
            log.info("定时任务 - 更新奖品消耗库存开始");
            StrategyAwardStockKeyVO strategyAwardStockKeyVO = raffleStock.takeQueueValue();
            if (null == strategyAwardStockKeyVO) return;
            log.info("定时任务 - 更新奖品消耗库存 strategyId:{} awardId:{}",
                    strategyAwardStockKeyVO.getStrategyId(), strategyAwardStockKeyVO.getAwardId());
            raffleStock.updateStrategyAwardStock(strategyAwardStockKeyVO.getStrategyId(), strategyAwardStockKeyVO.getAwardId());
        } catch (Exception e) {
            log.error("定时任务 - 更新奖品消耗库存失败", e);
        }
    }

}
