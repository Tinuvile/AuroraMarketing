package com.tinuvile.domain.award.model.aggregate;


import com.tinuvile.domain.award.model.entity.TaskEntity;
import com.tinuvile.domain.award.model.entity.UserAwardRecordEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tinuvile
 * @description 用户中奖记录聚合对象
 * @since 2026/1/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAwardRecordAggregate {

    private UserAwardRecordEntity userAwardRecordEntity;

    private TaskEntity taskEntity;

}
