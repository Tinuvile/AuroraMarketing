package com.tinuvile.infrastructure.persistent.dao;


import com.tinuvile.infrastructure.persistent.po.RuleTreeNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Tinuvile
 * @description 规则树节点DAO
 * @since 2025/12/1
 */
@Mapper
public interface IRuleTreeNodeDao {

    List<RuleTreeNode> queryRuleTreeNodeListByTreeId(String treeId);

    List<RuleTreeNode> queryRuleLocks(String[] treeIds);

}
