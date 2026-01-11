package com.tinuvile.trigger.job;


import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import com.tinuvile.domain.task.model.entity.TaskEntity;
import com.tinuvile.domain.task.service.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Tinuvile
 * @description 发送MQ消息任务队列
 * @since 2026/1/11
 */
@Slf4j
@Component()
public class SendMessageTaskJob {

    @Resource
    private ITaskService taskService;
    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private IDBRouterStrategy dbRouter;

    @Scheduled(cron = "0/5 * * * * ?")
    public void exec() {
        try {
            // 获取分库数量
            int dbCount = dbRouter.dbCount();

            // 逐个库扫描表【每个库一个任务表】
            for (int dbIndex = 1; dbIndex <= dbCount; dbIndex++) {
                int finalDbIndex = dbIndex;
                // 异步处理分库，使用线程池 executor 为每个库创建一个线程
                executor.execute(() -> {
                    try {
                        dbRouter.setDBKey(finalDbIndex);
                        dbRouter.setTBKey(0);
                        List<TaskEntity> taskEntities = taskService.queryNoSendMessageTaskList();
                        if (taskEntities.isEmpty()) return;
                        // 发送MQ消息
                        for (TaskEntity taskEntity : taskEntities) {
                            // 开启线程发送，提高发送效率，配置的线程池策略为 CallerRunPolicy， 在ThreadPoolConfig配置中有4个策略
                            // AbortPolicy：直接抛出 RejectedExecutionException 异常
                            // CallerRunPolicy：谁提交谁执行，提交任务的线程负责执行
                            // DiscardOldestPolicy：丢弃队列中最旧的任务，将新任务加入队列
                            // DiscardPolicy：直接丢弃新任务
                            executor.execute(() -> {
                                try {
                                    taskService.sendMessage(taskEntity);
                                    taskService.updateTaskSendMessageComplete(taskEntity.getUserId(), taskEntity.getMessageId());
                                } catch (Exception e) {
                                    log.error("定时任务，发送MQ消息失败，userId:{}，topic:{}", taskEntity.getUserId(), taskEntity.getTopic(), e);
                                    taskService.updateTaskSendMessageFail(taskEntity.getUserId(), taskEntity.getMessageId());
                                }
                            });
                        }
                    } finally {
                        dbRouter.clear();
                    }
                });
            }
        } catch (Exception e) {
            log.error("定时任务，扫描MQ任务表发送消息失败", e);
        } finally {
            dbRouter.clear();
        }
    }

}
