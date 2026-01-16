package com.tinuvile.domain.rebate.model.entity;


import com.tinuvile.domain.rebate.event.SendRebateMessageEvent;
import com.tinuvile.domain.rebate.model.valobj.TaskStateVO;
import com.tinuvile.types.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description 任务实体对象
 * @since 2026/1/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {

    private String userId;

    private String topic;

    private String messageId;

    private BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage> message;

    private TaskStateVO state;

}
