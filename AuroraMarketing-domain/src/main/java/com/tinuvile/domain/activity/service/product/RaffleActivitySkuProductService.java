package com.tinuvile.domain.activity.service.product;


import com.tinuvile.domain.activity.model.entity.SkuProductEntity;
import com.tinuvile.domain.activity.repository.IActivityRepository;
import com.tinuvile.domain.activity.service.IRaffleActivitySkuProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Tinuvile
 * @description sku商品服务
 * @since 2026/1/29
 */
@Service
public class RaffleActivitySkuProductService implements IRaffleActivitySkuProductService {

    @Resource
    private IActivityRepository repository;

    @Override
    public List<SkuProductEntity> querySkuProductEntityListByActivityId(Long activityId) {
        return repository.querySkuProductEntityListByActivityId(activityId);
    }

}
