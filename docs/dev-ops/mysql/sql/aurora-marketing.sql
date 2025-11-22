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


CREATE DATABASE IF NOT EXISTS `aurora-markting` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `aurora-markting`;


CREATE TABLE IF NOT EXISTS `strategy` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `strategy_id` int NOT NULL COMMENT '抽奖策略ID',
  `strategy_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖策略描述',
  `rule_models` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '规则模型',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='策略总表';

INSERT INTO `strategy` (`id`, `strategy_id`, `strategy_desc`, `rule_models`, `create_time`, `update_time`) VALUES
	(1, 10001, '抽奖策略1', NULL, '2025-11-22 20:53:07', '2025-11-22 20:53:07');


CREATE TABLE IF NOT EXISTS `strategy_award` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='抽奖策略详情表';

INSERT INTO `strategy_award` (`id`, `strategy_id`, `award_id`, `award_title`, `award_subtitle`, `award_count`, `award_count_surplus`, `award_rate`, `rule_models`, `sort`, `create_time`, `update_time`) VALUES
	(1, 10001, 101, '随机token数', NULL, 8000, 8000, 80.0000, 'rule_random', 1, '2025-11-22 20:54:25', '2025-11-22 21:06:58'),
	(2, 10001, 102, '5次上传图像', NULL, 1000, 1000, 10.0000, NULL, 2, '2025-11-22 20:55:09', '2025-11-22 20:57:00'),
	(3, 10001, 103, '5次高级模型对话【Claude4.5sonnet/Gemini3】', NULL, 500, 500, 5.0000, NULL, 3, '2025-11-22 20:55:44', '2025-11-22 20:58:11'),
	(4, 10001, 104, '10次高级模型对话【Claude4.5sonnet/Gemini3】', '抽奖3次后解锁', 400, 400, 3.0000, 'rule_lock', 4, '2025-11-22 20:56:25', '2025-11-22 21:12:13'),
	(5, 10001, 105, '5次使用AI绘图模型', '抽奖3次后解锁', 60, 60, 1.0000, 'rule_lock', 5, '2025-11-22 21:00:02', '2025-11-22 21:12:14'),
	(6, 10001, 106, '解锁一个月pro会员', '抽奖10次后解锁', 30, 30, 0.9990, 'rule_lock,rule_luck_award', 6, '2025-11-22 21:02:44', '2025-11-22 21:14:24'),
	(7, 10001, 107, '全年pro会员', '抽奖30次后解锁', 5, 5, 0.0010, 'rule_lock', 7, '2025-11-22 21:03:10', '2025-11-22 21:12:16');


CREATE TABLE IF NOT EXISTS `strategy_rule` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `strategy_id` int NOT NULL COMMENT '关联策略ID',
  `award_id` int DEFAULT NULL COMMENT '关联抽奖奖品ID',
  `rule_type` int NOT NULL DEFAULT '0' COMMENT '抽奖规则类型【1-策略规则、2-奖品规则】',
  `rule_model` varchar(16) NOT NULL COMMENT '抽奖规则类型',
  `rule_value` varchar(128) NOT NULL COMMENT '抽奖规则比值',
  `rule_desc` varchar(128) NOT NULL COMMENT '抽奖规则描述',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='策略规则表';

INSERT INTO `strategy_rule` (`id`, `strategy_id`, `award_id`, `rule_type`, `rule_model`, `rule_value`, `rule_desc`, `create_time`, `update_time`) VALUES
	(1, 10001, 101, 2, 'rule_random', '200,1000', '随机token数', '2025-11-22 21:05:25', '2025-11-22 21:14:10'),
	(2, 10001, 104, 2, 'rule_lock', '3', '抽奖3次后解锁', '2025-11-22 21:09:49', '2025-11-22 21:10:14'),
	(3, 10001, 105, 2, 'rule_lock', '3', '抽奖3次后解锁', '2025-11-22 21:11:08', '2025-11-22 21:11:08'),
	(4, 10001, 106, 2, 'rule_lock', '10', '抽奖10次后解锁', '2025-11-22 21:11:34', '2025-11-22 21:11:34'),
	(5, 10001, 107, 2, 'rule_lock', '30', '抽奖30次后解锁', '2025-11-22 21:12:01', '2025-11-22 21:12:01'),
	(6, 10001, 106, 2, 'rule_luck_award', '200,10000', '随机token兜底', '2025-11-22 21:13:56', '2025-11-22 21:13:56'),
	(7, 10001, NULL, 1, 'rule_weight', '30:101,102,103,104,105,106', '抽奖次数满30自动出', '2025-11-22 21:18:16', '2025-11-22 21:20:27'),
	(8, 10001, NULL, 1, 'rule_blacklist', '100', '黑名单用户100 token', '2025-11-22 21:23:46', '2025-11-22 21:23:46');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
