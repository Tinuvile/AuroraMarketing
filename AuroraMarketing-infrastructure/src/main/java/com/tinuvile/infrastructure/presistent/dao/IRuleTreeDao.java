package com.tinuvile.infrastructure.presistent.dao;


import com.tinuvile.infrastructure.presistent.po.RuleTree;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Tinuvile
 * @description 规则树DAO
 * @since 2025/12/1
 */
@Mapper
public interface IRuleTreeDao {

    RuleTree queryRuleTreeByTreeId(String treeId);

}
