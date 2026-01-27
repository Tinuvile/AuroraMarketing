package com.tinuvile.infrastructure.persistent.dao;


import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.tinuvile.infrastructure.persistent.po.UserCreditOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Tinuvile
 * @description 用户积分流水单Dao
 * @since 2026/1/27
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserCreditOrderDao {

    void insert(UserCreditOrder userCreditOrderReq);

}
