package com.tinuvile.domain.task.model.entity;


import lombok.Data;

/**
 * @author Tinuvile
 * @description 任务实体对象
 * @since 2026/1/11
 */
@Data
public class TaskEntity {

    /** 用户ID */
    private String userId;

    /** 消息主题 */
    private String topic;

    /** 消息编号 */
    private String messageId;

    /** 消息主体 */
    private String message;

}
