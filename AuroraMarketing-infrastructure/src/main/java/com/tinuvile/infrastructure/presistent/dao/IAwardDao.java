package com.tinuvile.infrastructure.presistent.dao;


import com.tinuvile.infrastructure.presistent.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Tinuvile
 * @description 奖品表DAO
 * @since 2025/11/23
 */
@Mapper
public interface IAwardDao {

    /**
     * 查询奖品列表（limit 10）
     * @return 奖品列表
     */
    List<Award> queryAwardList();

}
