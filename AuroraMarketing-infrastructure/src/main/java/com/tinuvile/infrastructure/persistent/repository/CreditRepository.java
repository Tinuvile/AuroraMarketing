package com.tinuvile.infrastructure.persistent.repository;


import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import com.alibaba.fastjson.JSON;
import com.tinuvile.domain.credit.model.aggregate.TradeAggregate;
import com.tinuvile.domain.credit.model.entity.CreditAccountEntity;
import com.tinuvile.domain.credit.model.entity.CreditOrderEntity;
import com.tinuvile.domain.credit.model.entity.TaskEntity;
import com.tinuvile.domain.credit.repository.ICreditRepository;
import com.tinuvile.infrastructure.event.EventPublisher;
import com.tinuvile.infrastructure.persistent.dao.ITaskDao;
import com.tinuvile.infrastructure.persistent.dao.IUserCreditAccountDao;
import com.tinuvile.infrastructure.persistent.dao.IUserCreditOrderDao;
import com.tinuvile.infrastructure.persistent.po.Task;
import com.tinuvile.infrastructure.persistent.po.UserCreditAccount;
import com.tinuvile.infrastructure.persistent.po.UserCreditOrder;
import com.tinuvile.infrastructure.redis.IRedisService;
import com.tinuvile.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Tinuvile
 * @description 用户积分仓储
 * @since 2026/1/27
 */
@Slf4j
@Repository
public class CreditRepository implements ICreditRepository {

    @Resource
    private IRedisService redisService;
    @Resource
    private IUserCreditAccountDao userCreditAccountDao;
    @Resource
    private IUserCreditOrderDao userCreditOrderDao;
    @Resource
    private ITaskDao taskDao;
    @Resource
    private IDBRouterStrategy dbRouter;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private EventPublisher eventPublisher;

    @Override
    public void saveUserCreditTradeOrder(TradeAggregate tradeAggregate) {
        String userId = tradeAggregate.getUserId();
        CreditAccountEntity creditAccountEntity = tradeAggregate.getCreditAccountEntity();
        CreditOrderEntity creditOrderEntity = tradeAggregate.getCreditOrderEntity();
        TaskEntity taskEntity = tradeAggregate.getTaskEntity();

        // 积分账户
        UserCreditAccount userCreditAccountReq = new UserCreditAccount();
        userCreditAccountReq.setUserId(userId);
        userCreditAccountReq.setTotalAmount(creditAccountEntity.getAdjustAmount());
        userCreditAccountReq.setAvailableAmount(creditAccountEntity.getAdjustAmount());

        // 积分订单
        UserCreditOrder userCreditOrderReq = new UserCreditOrder();
        userCreditOrderReq.setUserId(creditOrderEntity.getUserId());
        userCreditOrderReq.setOrderId(creditOrderEntity.getOrderId());
        userCreditOrderReq.setTradeName(creditOrderEntity.getTradeName().getName());
        userCreditOrderReq.setTradeType(creditOrderEntity.getTradeType().getCode());
        userCreditOrderReq.setTradeAmount(creditOrderEntity.getTradeAmount());
        userCreditOrderReq.setOutBusinessNo(creditOrderEntity.getOutBusinessNo());

        Task task = new Task();
        task.setUserId(taskEntity.getUserId());
        task.setTopic(taskEntity.getTopic());
        task.setMessageId(taskEntity.getMessageId());
        task.setMessage(JSON.toJSONString(taskEntity.getMessage()));
        task.setState(taskEntity.getState().getCode());

        RLock lock = redisService.getLock(Constants.RedisKey.USER_CREDIT_ACCOUNT_LOCK + userId + Constants.UNDERLINE + creditOrderEntity.getOutBusinessNo());
        try {
            lock.lock(3, TimeUnit.SECONDS);
            dbRouter.doRouter(userId);
            // 编程式事务
            transactionTemplate.execute(status -> {
                try {
                    // 保存积分账户
                    UserCreditAccount userCreditAccount = userCreditAccountDao.queryUserCreditAccount(userCreditAccountReq);
                    if (null == userCreditAccount) {
                        userCreditAccountDao.insert(userCreditAccountReq);
                    } else {
                        userCreditAccountDao.updateAddAmount(userCreditAccountReq);
                    }
                    // 保存账户订单
                    userCreditOrderDao.insert(userCreditOrderReq);
                    // 写入任务
                    taskDao.insert(task);
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("调整账户积分额度异常，唯一索引冲突 userId:{} orderId:{}", userId, creditOrderEntity.getOrderId(), e);
                } catch (Exception e) {
                    status.setRollbackOnly();
                    log.error("调整账户积分额度失败 userId:{} orderId:{}", userId, creditOrderEntity.getOrderId(), e);
                }
                return 1;
            });
        } finally {
            dbRouter.clear();
            lock.unlock();
        }

        try {
            // 发送消息【在事务外执行，这样失败还有任务补偿】
            eventPublisher.publish(task.getTopic(), task.getMessage());
            taskDao.updateTaskSendMessageComplete(task);
            log.info("调整账户积分记录，发送MQ消息完成 userId: {} orderId:{} topic: {}", userId, creditOrderEntity.getOrderId(), task.getTopic());
        } catch (Exception e) {
            log.error("调整账户积分记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic());
            taskDao.updateTaskSendMessageFail(task);
        }
    }

}
