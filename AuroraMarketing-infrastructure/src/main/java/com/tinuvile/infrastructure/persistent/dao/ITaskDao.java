package com.tinuvile.infrastructure.persistent.dao;


import cn.bugstack.middleware.db.router.annotation.DBRouter;
import com.tinuvile.infrastructure.persistent.po.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Tinuvile
 * @description 任务表，发送MQ消息
 * @since 2025/12/26
 */
@Mapper
public interface ITaskDao {

    void insert(Task task);

    @DBRouter
    void updateTaskSendMessageComplete(Task task);

    @DBRouter
    void updateTaskSendMessageFail(Task task);

    List<Task> queryNoSendMessageTaskList();

}
