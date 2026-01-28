-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.32 - MySQL Community Server - GPL
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  12.14.0.7165
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 aurora-marketing 的数据库结构
CREATE DATABASE IF NOT EXISTS `aurora-marketing` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `aurora-marketing`;

-- 导出  表 aurora-marketing.award 结构
CREATE TABLE IF NOT EXISTS `award` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `award_id` int NOT NULL COMMENT '抽奖奖品ID',
  `award_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖品对接标识',
  `award_config` varchar(32) NOT NULL COMMENT '奖品配置信息',
  `award_desc` varchar(128) NOT NULL COMMENT '奖品内容描述',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='奖品详情表';

-- 正在导出表  aurora-marketing.award 的数据：~9 rows (大约)
INSERT INTO `award` (`id`, `award_id`, `award_key`, `award_config`, `award_desc`, `create_time`, `update_time`) VALUES
	(1, 101, 'user_token_random', '200,1000', '赠送用户token', '2025-11-23 14:12:32', '2025-11-23 17:16:46'),
	(2, 102, 'user_credit_random', '1,1000', '赠送用户积分', '2025-11-23 17:16:41', '2025-11-23 17:16:41'),
	(3, 103, 'user_upload_image', '5', '允许用户上传图像对话', '2025-11-23 14:14:26', '2025-11-23 17:15:38'),
	(4, 104, 'user_access_high_model', '5', '允许用户使用高级模型', '2025-11-23 14:15:29', '2025-11-23 17:15:36'),
	(5, 105, 'user_access_high_model', '10', '允许用户使用高级模型', '2025-11-23 14:16:10', '2025-11-23 17:15:34'),
	(6, 106, 'user_access_draw_model', '5', '允许用户使用AI绘图模型', '2025-11-23 14:17:42', '2025-11-23 17:15:33'),
	(7, 107, 'user_pro_vip', '1', '赠送用户会员【月为单位】', '2025-11-23 14:18:29', '2025-11-23 17:15:31'),
	(8, 108, 'user_pro_vip', '12', '赠送用户会员【月为单位】', '2025-11-23 14:19:11', '2025-11-23 17:15:29'),
	(9, 100, 'user_token_blacklist', '100', '黑名单用户赠送token', '2025-11-25 14:36:17', '2025-11-25 14:36:17');

-- 导出  表 aurora-marketing.daily_behavior_rebate 结构
CREATE TABLE IF NOT EXISTS `daily_behavior_rebate` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `behavior_type` varchar(16) NOT NULL COMMENT '行为类型（sign 签到、openai_pay 支付）',
  `rebate_desc` varchar(128) NOT NULL COMMENT '返利描述',
  `rebate_type` varchar(16) NOT NULL COMMENT '返利类型（sku 活动库存充值商品、integral 用户活动积分）',
  `rebate_config` varchar(32) NOT NULL COMMENT '返利配置',
  `state` varchar(12) NOT NULL COMMENT '状态（open 开启、close 关闭）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_behavior_type` (`behavior_type`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='日常行为返利活动配置';

-- 正在导出表  aurora-marketing.daily_behavior_rebate 的数据：~2 rows (大约)
INSERT INTO `daily_behavior_rebate` (`id`, `behavior_type`, `rebate_desc`, `rebate_type`, `rebate_config`, `state`, `create_time`, `update_time`) VALUES
	(1, 'sign', '签到返利-sku额度', 'sku', '9011', 'open', '2026-01-16 13:35:04', '2026-01-16 13:35:04'),
	(2, 'sign', '签到返利-积分', 'integral', '10', 'open', '2026-01-16 13:35:28', '2026-01-16 13:35:28');

-- 导出  表 aurora-marketing.raffle_activity 结构
CREATE TABLE IF NOT EXISTS `raffle_activity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
  `activity_desc` varchar(128) NOT NULL COMMENT '活动描述',
  `begin_date_time` datetime NOT NULL COMMENT '开始时间',
  `end_date_time` datetime NOT NULL COMMENT '结束时间',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `state` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '活动状态',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_activity_id` (`activity_id`),
  UNIQUE KEY `uq_strategy_id` (`strategy_id`),
  KEY `idx_begin_date_time` (`begin_date_time`),
  KEY `idx_end_date_time` (`end_date_time`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='抽奖活动表';

-- 正在导出表  aurora-marketing.raffle_activity 的数据：~0 rows (大约)
INSERT INTO `raffle_activity` (`id`, `activity_id`, `activity_name`, `activity_desc`, `begin_date_time`, `end_date_time`, `strategy_id`, `state`, `create_time`, `update_time`) VALUES
	(1, 100301, '测试活动', '测试活动', '2025-12-12 11:54:18', '2026-12-30 14:28:24', 10001, 'open', '2025-12-09 11:55:06', '2026-01-12 15:54:55');

-- 导出  表 aurora-marketing.raffle_activity_count 结构
CREATE TABLE IF NOT EXISTS `raffle_activity_count` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `activity_count_id` bigint NOT NULL COMMENT '活动次数编号',
  `total_count` int NOT NULL COMMENT '总次数',
  `month_count` int NOT NULL COMMENT '月次数',
  `day_count` int NOT NULL COMMENT '日次数',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_activity_count_id` (`activity_count_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='抽奖活动次数配置表';

-- 正在导出表  aurora-marketing.raffle_activity_count 的数据：~0 rows (大约)
INSERT INTO `raffle_activity_count` (`id`, `activity_count_id`, `total_count`, `month_count`, `day_count`, `create_time`, `update_time`) VALUES
	(1, 11101, 1, 1, 1, '2025-12-09 21:49:14', '2025-12-09 21:49:14');

-- 导出  表 aurora-marketing.raffle_activity_sku 结构
CREATE TABLE IF NOT EXISTS `raffle_activity_sku` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `sku` bigint NOT NULL COMMENT '商品SKU - 每一个组合当作一个商品',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_count_id` bigint NOT NULL COMMENT '活动个人参与次数ID',
  `stock_count` int NOT NULL COMMENT '商品库存',
  `stock_count_surplus` int NOT NULL COMMENT '剩余库存',
  `product_amount` decimal(10,2) NOT NULL COMMENT '商品金额【积分】',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_sku` (`sku`),
  KEY `idx_activity_id_activity_count_id` (`activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='活动SKU表';

-- 正在导出表  aurora-marketing.raffle_activity_sku 的数据：~0 rows (大约)
INSERT INTO `raffle_activity_sku` (`id`, `sku`, `activity_id`, `activity_count_id`, `stock_count`, `stock_count_surplus`, `product_amount`, `create_time`, `update_time`) VALUES
	(1, 9011, 100301, 11101, 20, 20, 0.00, '2025-12-09 21:47:45', '2026-01-10 17:10:27');

-- 导出  表 aurora-marketing.rule_tree 结构
CREATE TABLE IF NOT EXISTS `rule_tree` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `tree_id` varchar(32) NOT NULL COMMENT '规则树ID',
  `tree_name` varchar(64) NOT NULL COMMENT '规则树名称',
  `tree_desc` varchar(128) DEFAULT NULL COMMENT '规则树描述',
  `tree_root_rule_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规则树根入口规则',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='规则树';

-- 正在导出表  aurora-marketing.rule_tree 的数据：~2 rows (大约)
INSERT INTO `rule_tree` (`id`, `tree_id`, `tree_name`, `tree_desc`, `tree_root_rule_key`, `create_time`, `update_time`) VALUES
	(1, 'tree_lock', '规则树', '规则树', 'rule_lock', '2025-12-01 15:07:33', '2025-12-01 15:07:33');

-- 导出  表 aurora-marketing.rule_tree_node 结构
CREATE TABLE IF NOT EXISTS `rule_tree_node` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `tree_id` varchar(32) NOT NULL COMMENT '规则树ID',
  `rule_key` varchar(32) NOT NULL COMMENT '规则Key',
  `rule_desc` varchar(64) NOT NULL COMMENT '规则描述',
  `rule_value` varchar(128) DEFAULT NULL COMMENT '规则比值',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='规则树节点';

-- 正在导出表  aurora-marketing.rule_tree_node 的数据：~3 rows (大约)
INSERT INTO `rule_tree_node` (`id`, `tree_id`, `rule_key`, `rule_desc`, `rule_value`, `create_time`, `update_time`) VALUES
	(1, 'tree_lock', 'rule_lock', '限定用户已完成N次抽奖后解锁', '1', '2025-12-01 15:08:08', '2025-12-01 15:08:08'),
	(2, 'tree_lock', 'rule_luck_award', '兜底奖品随机token', '102:20,100', '2025-12-01 15:09:24', '2026-01-23 20:20:44'),
	(3, 'tree_lock', 'rule_stock', '库存扣减规则', 'NULL', '2025-12-01 15:11:21', '2025-12-01 15:11:21');

-- 导出  表 aurora-marketing.rule_tree_node_line 结构
CREATE TABLE IF NOT EXISTS `rule_tree_node_line` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `tree_id` varchar(32) NOT NULL COMMENT '规则树ID',
  `rule_node_from` varchar(32) NOT NULL COMMENT '规则树Key节点From',
  `rule_node_to` varchar(32) NOT NULL COMMENT '规则树Key节点To',
  `rule_limit_type` varchar(8) NOT NULL COMMENT '限定类型；1:=、2:>、3:<、4:>=、5:<=、6:enum[枚举范围]；',
  `rule_limit_value` varchar(32) NOT NULL COMMENT '限定值（到下个节点）',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='规则树节点连线';

-- 正在导出表  aurora-marketing.rule_tree_node_line 的数据：~3 rows (大约)
INSERT INTO `rule_tree_node_line` (`id`, `tree_id`, `rule_node_from`, `rule_node_to`, `rule_limit_type`, `rule_limit_value`, `create_time`, `update_time`) VALUES
	(1, 'tree_lock', 'rule_lock', 'rule_stock', 'EQUAL', 'ALLOW', '2025-12-01 15:12:03', '2025-12-01 15:12:03'),
	(2, 'tree_lock', 'rule_lock', 'rule_luck_award', 'EQUAL', 'TAKE_OVER', '2025-12-01 15:12:45', '2025-12-01 15:12:45'),
	(3, 'tree_lock', 'rule_stock', 'rule_luck_award', 'EQUAL', 'ALLOW', '2025-12-01 15:13:06', '2025-12-02 20:07:39');

-- 导出  表 aurora-marketing.strategy 结构
CREATE TABLE IF NOT EXISTS `strategy` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `strategy_id` int NOT NULL COMMENT '抽奖策略ID',
  `strategy_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖策略描述',
  `rule_models` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '规则模型',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='策略总表';

-- 正在导出表  aurora-marketing.strategy 的数据：~3 rows (大约)
INSERT INTO `strategy` (`id`, `strategy_id`, `strategy_desc`, `rule_models`, `create_time`, `update_time`) VALUES
	(1, 10001, '抽奖策略1', 'rule_blacklist,rule_weight', '2025-11-22 20:53:07', '2025-11-27 21:27:34'),
	(2, 10002, '抽奖策略2 - 验证lock', NULL, '2025-11-26 20:13:02', '2025-12-01 20:38:10'),
	(4, 10003, '抽奖策略3 - 验证库存扣减', NULL, '2025-12-02 18:56:41', '2025-12-02 18:56:41');

-- 导出  表 aurora-marketing.strategy_award 结构
CREATE TABLE IF NOT EXISTS `strategy_award` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `strategy_id` int NOT NULL COMMENT '抽奖策略ID',
  `award_id` int NOT NULL COMMENT '奖品ID',
  `award_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖奖品标题',
  `award_subtitle` varchar(128) DEFAULT NULL COMMENT '抽奖奖品副标题',
  `award_count` int NOT NULL COMMENT '奖品库存总量',
  `award_count_surplus` int NOT NULL COMMENT '奖品库存剩余',
  `award_rate` decimal(6,4) NOT NULL COMMENT '奖品中奖概率',
  `rule_models` varchar(256) DEFAULT NULL COMMENT '规则模型',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='抽奖策略详情表';

-- 正在导出表  aurora-marketing.strategy_award 的数据：~14 rows (大约)
INSERT INTO `strategy_award` (`id`, `strategy_id`, `award_id`, `award_title`, `award_subtitle`, `award_count`, `award_count_surplus`, `award_rate`, `rule_models`, `sort`, `create_time`, `update_time`) VALUES
	(1, 10001, 101, '随机token数', NULL, 8000, 8000, 40.0000, 'tree_lock', 1, '2025-11-22 20:54:25', '2026-01-23 14:48:41'),
	(2, 10001, 102, '随机积分数', NULL, 10000, 10000, 40.0000, 'tree_lock', 2, '2025-11-23 17:13:34', '2026-01-23 14:48:48'),
	(3, 10001, 103, '5次上传图像对话', NULL, 1000, 999, 10.0000, 'tree_lock', 3, '2025-11-22 20:55:09', '2026-01-23 14:51:10'),
	(4, 10001, 104, '5次高级模型对话【Claude4.5sonnet/Gemini3】', NULL, 500, 500, 5.0000, 'tree_lock', 4, '2025-11-22 20:55:44', '2025-12-05 17:53:38'),
	(5, 10001, 105, '10次高级模型对话【Claude4.5sonnet/Gemini3】', '抽奖3次后解锁', 400, 398, 3.0000, 'tree_lock', 5, '2025-11-22 20:56:25', '2026-01-23 14:49:50'),
	(6, 10001, 106, '5次使用AI绘图模型', '抽奖3次后解锁', 60, 60, 1.0000, 'tree_lock', 6, '2025-11-22 21:00:02', '2026-01-23 14:49:54'),
	(7, 10001, 107, '解锁一个月pro会员', '抽奖10次后解锁', 30, 30, 0.9990, 'tree_lock', 7, '2025-11-22 21:02:44', '2026-01-23 14:50:01'),
	(8, 10001, 108, '全年pro会员', '抽奖30次后解锁', 5, 5, 0.0010, 'tree_lock', 8, '2025-11-22 21:03:10', '2026-01-23 14:50:04'),
	(9, 10002, 105, '10次高级模型对话【Claude4.5sonnet/Gemini3】', '抽奖3次后解锁', 400, 387, 40.0000, 'tree_lock', 2, '2025-11-26 20:15:11', '2025-12-05 15:29:50'),
	(10, 10002, 101, '随机token数', NULL, 8000, 8000, 40.0000, 'tree_lock', 0, '2026-01-12 16:13:27', '2026-01-12 16:13:27'),
	(11, 10003, 102, '随机积分数', NULL, 15, 4, 0.4500, 'tree_lock', 1, '2025-12-02 18:58:19', '2025-12-05 15:30:05'),
	(12, 10003, 103, '5次上传图像对话', NULL, 45, 32, 0.5500, 'tree_lock', 0, '2025-12-02 18:59:22', '2025-12-04 20:41:00'),
	(13, 10001, 100, '黑名单用户赠送token', NULL, 10000, 10000, 0.0000, NULL, 0, '2025-12-04 17:44:33', '2026-01-23 20:18:41'),
	(14, 10003, 101, '随机token数', NULL, 80, 80, 0.8000, 'tree_lock', 0, '2026-01-12 16:14:11', '2026-01-12 16:14:11');

-- 导出  表 aurora-marketing.strategy_rule 结构
CREATE TABLE IF NOT EXISTS `strategy_rule` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `strategy_id` int NOT NULL COMMENT '关联策略ID',
  `award_id` int DEFAULT NULL COMMENT '关联抽奖奖品ID',
  `rule_type` int NOT NULL DEFAULT '0' COMMENT '抽奖规则类型【1-策略规则、2-奖品规则】',
  `rule_model` varchar(16) NOT NULL COMMENT '抽奖规则类型',
  `rule_value` varchar(128) NOT NULL COMMENT '抽奖规则比值',
  `rule_desc` varchar(128) NOT NULL COMMENT '抽奖规则描述',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_strategy_id_rule_model` (`strategy_id`,`rule_model`),
  KEY `idx_strategy_id_award_id` (`strategy_id`,`award_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='策略规则表';

-- 正在导出表  aurora-marketing.strategy_rule 的数据：~5 rows (大约)
INSERT INTO `strategy_rule` (`id`, `strategy_id`, `award_id`, `rule_type`, `rule_model`, `rule_value`, `rule_desc`, `create_time`, `update_time`) VALUES
	(7, 10001, 107, 2, 'rule_luck_award', '102:20,100', '随机token兜底', '2025-11-22 21:13:56', '2026-01-23 20:21:29'),
	(8, 10001, NULL, 1, 'rule_weight', '60:107 30:103,104,105,106', '抽奖次数满30自动出', '2025-11-22 21:18:16', '2026-01-21 17:20:03'),
	(9, 10001, NULL, 1, 'rule_blacklist', '100:user001,user002', '黑名单用户100 token', '2025-11-22 21:23:46', '2025-11-25 14:37:58'),
	(10, 10002, 105, 2, 'rule_lock', '3', '抽奖3次后解锁', '2025-11-26 20:16:13', '2025-11-26 20:19:44'),
	(12, 10001, 105, 1, 'rule_lock', '5', '抽奖5次后解锁', '2026-01-21 17:18:53', '2026-01-21 17:21:08');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
