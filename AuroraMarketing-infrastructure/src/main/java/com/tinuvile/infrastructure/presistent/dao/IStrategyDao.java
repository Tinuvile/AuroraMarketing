package com.tinuvile.infrastructure.presistent.dao;


import com.tinuvile.infrastructure.presistent.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Tinuvile
 * @description 策略总表DAO
 * @since 2025/11/23
 */
@Mapper
public interface IStrategyDao {

    List<Strategy> queryStrategyList();

}
