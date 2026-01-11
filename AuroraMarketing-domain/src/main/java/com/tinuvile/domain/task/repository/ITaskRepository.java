package com.tinuvile.domain.task.repository;


import com.tinuvile.domain.task.model.entity.TaskEntity;

import java.util.List;

/**
 * @author Tinuvile
 * @description 任务服务仓储接口
 * @since 2026/1/11
 */
public interface ITaskRepository {

    List<TaskEntity> queryNoSendMessageTaskList();

    void sendMessage(TaskEntity taskEntity);

    void updateTaskSendMessageComplete(String userId, String messageId);

    void updateTaskSendMessageFail(String userId, String messageId);

}
