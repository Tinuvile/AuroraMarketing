-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.32 - MySQL Community Server - GPL
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  12.13.0.7147
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 aurora-marketing-01 的数据库结构
CREATE DATABASE IF NOT EXISTS `aurora-marketing-01` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `aurora-marketing-01`;

-- 导出  表 aurora-marketing-01.raffle_activity_account 结构
CREATE TABLE IF NOT EXISTS `raffle_activity_account` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `total_count` int NOT NULL COMMENT '总次数',
  `total_count_surplus` int NOT NULL COMMENT '总次数-剩余',
  `month_count` int NOT NULL COMMENT '月次数',
  `month_count_surplus` int NOT NULL COMMENT '月次数-剩余',
  `day_count` int NOT NULL COMMENT '日次数',
  `day_count_surplus` int NOT NULL COMMENT '日次数-剩余',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_user_id_activity_id` (`user_id`,`activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='抽奖活动账户表';

-- 正在导出表  aurora-marketing-01.raffle_activity_account 的数据：~0 rows (大约)
INSERT INTO `raffle_activity_account` (`id`, `user_id`, `activity_id`, `total_count`, `total_count_surplus`, `month_count`, `month_count_surplus`, `day_count`, `day_count_surplus`, `create_time`, `update_time`) VALUES
	(1, 'tinuvile', 100301, 70, 70, 70, 70, 70, 70, '2025-12-22 17:03:09', '2026-01-11 14:31:50');

-- 导出  表 aurora-marketing-01.raffle_activity_account_day 结构
CREATE TABLE IF NOT EXISTS `raffle_activity_account_day` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `day` varchar(10) NOT NULL COMMENT '日期（yyyy-mm-dd）',
  `day_count` int NOT NULL COMMENT '日次数',
  `day_count_surplus` int NOT NULL COMMENT '日次数-剩余',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_user_id_activity_id_day` (`user_id`,`activity_id`,`day`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='抽奖活动账户表-日次数';

-- 正在导出表  aurora-marketing-01.raffle_activity_account_day 的数据：~0 rows (大约)
INSERT INTO `raffle_activity_account_day` (`id`, `user_id`, `activity_id`, `day`, `day_count`, `day_count_surplus`, `create_time`, `update_time`) VALUES
	(1, 'tinuvile', 100301, '2026-01-10', 70, 70, '2026-01-10 17:13:31', '2026-01-10 17:13:31');

-- 导出  表 aurora-marketing-01.raffle_activity_account_month 结构
CREATE TABLE IF NOT EXISTS `raffle_activity_account_month` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `month` varchar(7) NOT NULL COMMENT '月（yyyy-mm）',
  `month_count` int NOT NULL COMMENT '月次数',
  `month_count_surplus` int NOT NULL COMMENT '月次数-剩余',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_user_id_activity_id_month` (`user_id`,`activity_id`,`month`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='抽奖活动账户表-月次数';

-- 正在导出表  aurora-marketing-01.raffle_activity_account_month 的数据：~0 rows (大约)
INSERT INTO `raffle_activity_account_month` (`id`, `user_id`, `activity_id`, `month`, `month_count`, `month_count_surplus`, `create_time`, `update_time`) VALUES
	(1, 'tinuvile', 100301, '一月', 70, 70, '2026-01-10 17:13:31', '2026-01-10 17:13:31');

-- 导出  表 aurora-marketing-01.raffle_activity_order_000 结构
CREATE TABLE IF NOT EXISTS `raffle_activity_order_000` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `sku` bigint NOT NULL COMMENT '商品SKU',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '策略ID',
  `order_id` varchar(12) NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `total_count` int NOT NULL COMMENT '总次数',
  `month_count` int NOT NULL COMMENT '月次数',
  `day_count` int NOT NULL COMMENT '日次数',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
  `out_business_no` varchar(64) NOT NULL COMMENT '业务防重ID - 外部透传，确保幂等',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_order_id` (`order_id`),
  UNIQUE KEY `uq_out_business_no` (`out_business_no`),
  KEY `idx_user_id_activity_id` (`user_id`,`activity_id`,`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='抽奖活动单';

-- 正在导出表  aurora-marketing-01.raffle_activity_order_000 的数据：~0 rows (大约)

-- 导出  表 aurora-marketing-01.raffle_activity_order_001 结构
CREATE TABLE IF NOT EXISTS `raffle_activity_order_001` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `sku` bigint NOT NULL COMMENT '商品SKU',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '策略ID',
  `order_id` varchar(12) NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `total_count` int NOT NULL COMMENT '总次数',
  `month_count` int NOT NULL COMMENT '月次数',
  `day_count` int NOT NULL COMMENT '日次数',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务防重ID - 外部透传，确保幂等',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_order_id` (`order_id`),
  UNIQUE KEY `uq_out_business_no` (`out_business_no`),
  KEY `idx_user_id_activity_id` (`user_id`,`activity_id`,`state`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='抽奖活动单';

-- 正在导出表  aurora-marketing-01.raffle_activity_order_001 的数据：~0 rows (大约)
INSERT INTO `raffle_activity_order_001` (`id`, `user_id`, `sku`, `activity_id`, `activity_name`, `strategy_id`, `order_id`, `order_time`, `total_count`, `month_count`, `day_count`, `state`, `out_business_no`, `create_time`, `update_time`) VALUES
	(4, 'eOMtThyhVNLWUZNRcBaQKxI', -5106534569952410475, 100301, '测试活动', 100006, '969591958808', '2025-12-22 09:09:26', 1, 1, 1, 'not_used', 'NVGaJCDCrvwh', '2025-12-22 17:09:27', '2025-12-22 17:09:27');

-- 导出  表 aurora-marketing-01.raffle_activity_order_002 结构
CREATE TABLE IF NOT EXISTS `raffle_activity_order_002` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `sku` bigint NOT NULL COMMENT '商品SKU',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '策略ID',
  `order_id` varchar(12) NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `total_count` int NOT NULL COMMENT '总次数',
  `month_count` int NOT NULL COMMENT '月次数',
  `day_count` int NOT NULL COMMENT '日次数',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务防重ID - 外部透传，确保幂等',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_order_id` (`order_id`),
  UNIQUE KEY `uq_out_business_no` (`out_business_no`),
  KEY `idx_user_id_activity_id` (`user_id`,`activity_id`,`state`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='抽奖活动单';

-- 正在导出表  aurora-marketing-01.raffle_activity_order_002 的数据：~0 rows (大约)
INSERT INTO `raffle_activity_order_002` (`id`, `user_id`, `sku`, `activity_id`, `activity_name`, `strategy_id`, `order_id`, `order_time`, `total_count`, `month_count`, `day_count`, `state`, `out_business_no`, `create_time`, `update_time`) VALUES
	(4, 'yedUsFwdkelQbxeTeQOvaScfqIOOmaa', -167885730524958550, 100301, '测试活动', 100006, '802625426222', '2025-12-22 09:09:27', 1, 1, 1, 'not_used', 'QbpnRr9erzXl', '2025-12-22 17:09:27', '2025-12-22 17:09:27');

-- 导出  表 aurora-marketing-01.raffle_activity_order_003 结构
CREATE TABLE IF NOT EXISTS `raffle_activity_order_003` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `sku` bigint NOT NULL COMMENT '商品SKU',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '策略ID',
  `order_id` varchar(12) NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `total_count` int NOT NULL COMMENT '总次数',
  `month_count` int NOT NULL COMMENT '月次数',
  `day_count` int NOT NULL COMMENT '日次数',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务防重ID - 外部透传，确保幂等',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_order_id` (`order_id`),
  UNIQUE KEY `uq_out_business_no` (`out_business_no`),
  KEY `idx_user_id_activity_id` (`user_id`,`activity_id`,`state`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='抽奖活动单';

-- 正在导出表  aurora-marketing-01.raffle_activity_order_003 的数据：~21 rows (大约)
INSERT INTO `raffle_activity_order_003` (`id`, `user_id`, `sku`, `activity_id`, `activity_name`, `strategy_id`, `order_id`, `order_time`, `total_count`, `month_count`, `day_count`, `state`, `out_business_no`, `create_time`, `update_time`) VALUES
	(1, 'tinuvile', 9011, 100301, '测试活动', 100006, '888405655602', '2025-12-22 09:03:09', 1, 1, 1, 'complete', '700091009111', '2025-12-22 17:03:09', '2025-12-22 17:03:09'),
	(2, 'tinuvile', 9011, 100301, '测试活动', 100006, '428519448658', '2026-01-10 09:10:14', 1, 1, 1, 'complete', '194830630550', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(3, 'tinuvile', 9011, 100301, '测试活动', 100006, '437670659314', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '731111409451', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(4, 'tinuvile', 9011, 100301, '测试活动', 100006, '460150550442', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '644011171894', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(5, 'tinuvile', 9011, 100301, '测试活动', 100006, '866744383346', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '727278181756', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(6, 'tinuvile', 9011, 100301, '测试活动', 100006, '995953867375', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '714953408817', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(7, 'tinuvile', 9011, 100301, '测试活动', 100006, '535948308546', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '352724605975', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(8, 'tinuvile', 9011, 100301, '测试活动', 100006, '204080513432', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '620766783764', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(9, 'tinuvile', 9011, 100301, '测试活动', 100006, '070695437113', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '280988888386', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(10, 'tinuvile', 9011, 100301, '测试活动', 100006, '033092983101', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '590543725112', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(11, 'tinuvile', 9011, 100301, '测试活动', 100006, '142818806672', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '143189383664', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(12, 'tinuvile', 9011, 100301, '测试活动', 100006, '732311781931', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '865083829206', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(13, 'tinuvile', 9011, 100301, '测试活动', 100006, '695151906382', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '789697277495', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(14, 'tinuvile', 9011, 100301, '测试活动', 100006, '080136640133', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '173936595891', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(15, 'tinuvile', 9011, 100301, '测试活动', 100006, '040925928090', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '167291543524', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(16, 'tinuvile', 9011, 100301, '测试活动', 100006, '875008855025', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '429603847558', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(17, 'tinuvile', 9011, 100301, '测试活动', 100006, '237344625986', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '923815316378', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(18, 'tinuvile', 9011, 100301, '测试活动', 100006, '281756838956', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '887041390541', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(19, 'tinuvile', 9011, 100301, '测试活动', 100006, '730481329821', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '504951048542', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(20, 'tinuvile', 9011, 100301, '测试活动', 100006, '229509197148', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '098272690966', '2026-01-10 17:10:14', '2026-01-10 17:10:14'),
	(21, 'tinuvile', 9011, 100301, '测试活动', 100006, '812927544509', '2026-01-10 09:10:15', 1, 1, 1, 'complete', '596725650301', '2026-01-10 17:10:15', '2026-01-10 17:10:15');

-- 导出  表 aurora-marketing-01.task 结构
CREATE TABLE IF NOT EXISTS `task` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `topic` varchar(32) NOT NULL COMMENT '消息主题',
  `message_id` varchar(11) NOT NULL COMMENT '消息编号',
  `message` varchar(512) NOT NULL COMMENT '消息主体',
  `state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '任务状态；create-创建、completed-完成、fail-失败',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_message_id` (`message_id`),
  KEY `idx_state` (`state`),
  KEY `idx_update_time` (`update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='任务表，发送MQ';

-- 正在导出表  aurora-marketing-01.task 的数据：~0 rows (大约)

-- 导出  表 aurora-marketing-01.user_award_record_000 结构
CREATE TABLE IF NOT EXISTS `user_award_record_000` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
  `award_id` int NOT NULL COMMENT '奖品ID',
  `award_title` varchar(128) NOT NULL COMMENT '奖品标题（名称）',
  `award_time` datetime NOT NULL COMMENT '中奖时间',
  `award_state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_order_id` (`order_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_activity_id` (`activity_id`),
  KEY `idx_strategy_id` (`strategy_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户中奖记录表';

-- 正在导出表  aurora-marketing-01.user_award_record_000 的数据：~0 rows (大约)

-- 导出  表 aurora-marketing-01.user_award_record_001 结构
CREATE TABLE IF NOT EXISTS `user_award_record_001` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
  `award_id` int NOT NULL COMMENT '奖品ID',
  `award_title` varchar(128) NOT NULL COMMENT '奖品标题（名称）',
  `award_time` datetime NOT NULL COMMENT '中奖时间',
  `award_state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_order_id` (`order_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_activity_id` (`activity_id`),
  KEY `idx_strategy_id` (`strategy_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户中奖记录表';

-- 正在导出表  aurora-marketing-01.user_award_record_001 的数据：~0 rows (大约)

-- 导出  表 aurora-marketing-01.user_award_record_002 结构
CREATE TABLE IF NOT EXISTS `user_award_record_002` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
  `award_id` int NOT NULL COMMENT '奖品ID',
  `award_title` varchar(128) NOT NULL COMMENT '奖品标题（名称）',
  `award_time` datetime NOT NULL COMMENT '中奖时间',
  `award_state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_order_id` (`order_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_activity_id` (`activity_id`),
  KEY `idx_strategy_id` (`strategy_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户中奖记录表';

-- 正在导出表  aurora-marketing-01.user_award_record_002 的数据：~0 rows (大约)

-- 导出  表 aurora-marketing-01.user_award_record_003 结构
CREATE TABLE IF NOT EXISTS `user_award_record_003` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
  `award_id` int NOT NULL COMMENT '奖品ID',
  `award_title` varchar(128) NOT NULL COMMENT '奖品标题（名称）',
  `award_time` datetime NOT NULL COMMENT '中奖时间',
  `award_state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_order_id` (`order_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_activity_id` (`activity_id`),
  KEY `idx_strategy_id` (`strategy_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户中奖记录表';

-- 正在导出表  aurora-marketing-01.user_award_record_003 的数据：~0 rows (大约)

-- 导出  表 aurora-marketing-01.user_raffle_order_000 结构
CREATE TABLE IF NOT EXISTS `user_raffle_order_000` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `order_state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_order_id` (`order_id`),
  KEY `idx_user_id_activity_id` (`user_id`,`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户抽奖订单表';

-- 正在导出表  aurora-marketing-01.user_raffle_order_000 的数据：~0 rows (大约)

-- 导出  表 aurora-marketing-01.user_raffle_order_001 结构
CREATE TABLE IF NOT EXISTS `user_raffle_order_001` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `order_state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_order_id` (`order_id`),
  KEY `idx_user_id_activity_id` (`user_id`,`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户抽奖订单表';

-- 正在导出表  aurora-marketing-01.user_raffle_order_001 的数据：~0 rows (大约)

-- 导出  表 aurora-marketing-01.user_raffle_order_002 结构
CREATE TABLE IF NOT EXISTS `user_raffle_order_002` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `order_state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_order_id` (`order_id`),
  KEY `idx_user_id_activity_id` (`user_id`,`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户抽奖订单表';

-- 正在导出表  aurora-marketing-01.user_raffle_order_002 的数据：~0 rows (大约)

-- 导出  表 aurora-marketing-01.user_raffle_order_003 结构
CREATE TABLE IF NOT EXISTS `user_raffle_order_003` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `order_state` varchar(16) NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_order_id` (`order_id`),
  KEY `idx_user_id_activity_id` (`user_id`,`activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户抽奖订单表';

-- 正在导出表  aurora-marketing-01.user_raffle_order_003 的数据：~0 rows (大约)
INSERT INTO `user_raffle_order_003` (`id`, `user_id`, `activity_id`, `activity_name`, `strategy_id`, `order_id`, `order_time`, `order_state`, `create_time`, `update_time`) VALUES
	(1, 'tinuvile', 100301, '测试活动', 100006, '103969569963', '2026-01-10 09:13:32', 'create', '2026-01-10 17:13:31', '2026-01-10 17:13:31');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
