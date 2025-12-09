package com.tinuvile.domain.activity.service;


import com.tinuvile.domain.activity.repository.IActivityRepository;
import org.springframework.stereotype.Service;

/**
 * @author Tinuvile
 * @description 抽奖活动服务
 * @since 2025/12/9
 */
@Service
public class RaffleActivityService extends AbstractRaffleActivity {

    public RaffleActivityService(IActivityRepository activityRepository) {
        super(activityRepository);
    }

}
