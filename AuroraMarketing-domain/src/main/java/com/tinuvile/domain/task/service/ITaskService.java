package com.tinuvile.domain.task.service;


import com.tinuvile.domain.task.model.entity.TaskEntity;

import java.util.List;

/**
 * @author Tinuvile
 * @description 消息任务服务接口
 * @since 2026/1/11
 */
public interface ITaskService {

    /**
     * 查询发送MQ失败和超过1分钟未发送的MQ
     *
     * @return 未发送消息的任务列表10条
     */
    List<TaskEntity> queryNoSendMessageTaskList();

    void sendMessage(TaskEntity taskEntity);

    void updateTaskSendMessageComplete(String userId, String messageId);

    void updateTaskSendMessageFail(String userId, String messageId);

}
