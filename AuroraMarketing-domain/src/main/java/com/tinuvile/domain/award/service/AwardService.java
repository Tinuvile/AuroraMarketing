package com.tinuvile.domain.award.service;


import com.tinuvile.domain.award.event.SendAwardMessageEvent;
import com.tinuvile.domain.award.model.aggregate.UserAwardRecordAggregate;
import com.tinuvile.domain.award.model.entity.TaskEntity;
import com.tinuvile.domain.award.model.entity.UserAwardRecordEntity;
import com.tinuvile.domain.award.model.valobj.TaskStateVO;
import com.tinuvile.domain.award.repository.IAwardRepository;
import com.tinuvile.types.event.BaseEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Tinuvile
 * @description 奖品服务实现类
 * @since 2026/1/11
 */
@Service
public class AwardService implements IAwardService {

    @Resource
    private IAwardRepository iAwardRepository;
    @Resource
    private SendAwardMessageEvent sendAwardMessageEvent;

    @Override
    public void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity) {

        // 构建消息对象
        SendAwardMessageEvent.SendAwardMessage sendAwardMessage = new SendAwardMessageEvent.SendAwardMessage();
        sendAwardMessage.setUserId(userAwardRecordEntity.getUserId());
        sendAwardMessage.setAwardId(userAwardRecordEntity.getAwardId());
        sendAwardMessage.setAwardTitle(userAwardRecordEntity.getAwardTitle());

        BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage> sendAwardMessageEventMessage =
                sendAwardMessageEvent.buildEventMessage(sendAwardMessage);

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setUserId(userAwardRecordEntity.getUserId());
        taskEntity.setTopic(sendAwardMessageEvent.topic());
        taskEntity.setMessageId(sendAwardMessageEventMessage.getId());
        taskEntity.setMessage(sendAwardMessageEventMessage);
        taskEntity.setState(TaskStateVO.create);

        // 构建聚合对象
        UserAwardRecordAggregate userAwardRecordAggregate = UserAwardRecordAggregate.builder()
                .taskEntity(taskEntity)
                .userAwardRecordEntity(userAwardRecordEntity)
                .build();

        // 存储聚合对象
        iAwardRepository.saveUserAwardRecord(userAwardRecordAggregate);

    }

}
