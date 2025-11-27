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


-- 导出 aurora-marketing 的数据库结构
CREATE DATABASE IF NOT EXISTS `aurora-marketing` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `aurora-marketing`;

-- 导出  表 aurora-marketing.award 结构
CREATE TABLE IF NOT EXISTS `award` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `award_id` int NOT NULL COMMENT '抽奖奖品ID',
  `award_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖品对接标识',
  `award_config` varchar(32) NOT NULL COMMENT '奖品配置信息',
  `award_desc` varchar(128) NOT NULL COMMENT '奖品内容描述',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
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

-- 导出  表 aurora-marketing.strategy 结构
CREATE TABLE IF NOT EXISTS `strategy` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `strategy_id` int NOT NULL COMMENT '抽奖策略ID',
  `strategy_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖策略描述',
  `rule_models` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '规则模型',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='策略总表';

-- 正在导出表  aurora-marketing.strategy 的数据：~2 rows (大约)
INSERT INTO `strategy` (`id`, `strategy_id`, `strategy_desc`, `rule_models`, `create_time`, `update_time`) VALUES
	(1, 10001, '抽奖策略1', 'rule_blacklist,rule_weight', '2025-11-22 20:53:07', '2025-11-27 21:27:34'),
	(2, 10002, '抽奖策略2 - 测试rule_lock', NULL, '2025-11-26 20:13:02', '2025-11-26 20:13:02');

-- 导出  表 aurora-marketing.strategy_award 结构
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='抽奖策略详情表';

-- 正在导出表  aurora-marketing.strategy_award 的数据：~9 rows (大约)
INSERT INTO `strategy_award` (`id`, `strategy_id`, `award_id`, `award_title`, `award_subtitle`, `award_count`, `award_count_surplus`, `award_rate`, `rule_models`, `sort`, `create_time`, `update_time`) VALUES
	(1, 10001, 101, '随机token数', NULL, 8000, 8000, 40.0000, 'rule_random', 1, '2025-11-22 20:54:25', '2025-11-23 17:14:50'),
	(2, 10001, 102, '随机积分数', NULL, 10000, 10000, 40.0000, 'rule_random', 1, '2025-11-23 17:13:34', '2025-11-23 17:15:03'),
	(3, 10001, 103, '5次上传图像对话', NULL, 1000, 1000, 10.0000, NULL, 2, '2025-11-22 20:55:09', '2025-11-23 17:14:35'),
	(4, 10001, 104, '5次高级模型对话【Claude4.5sonnet/Gemini3】', NULL, 500, 500, 5.0000, NULL, 3, '2025-11-22 20:55:44', '2025-11-23 17:14:37'),
	(5, 10001, 105, '10次高级模型对话【Claude4.5sonnet/Gemini3】', '抽奖3次后解锁', 400, 400, 3.0000, 'rule_lock', 4, '2025-11-22 20:56:25', '2025-11-23 17:14:40'),
	(6, 10001, 106, '5次使用AI绘图模型', '抽奖3次后解锁', 60, 60, 1.0000, 'rule_lock', 5, '2025-11-22 21:00:02', '2025-11-23 17:14:42'),
	(7, 10001, 107, '解锁一个月pro会员', '抽奖10次后解锁', 30, 30, 0.9990, 'rule_lock,rule_luck_award', 6, '2025-11-22 21:02:44', '2025-11-23 17:14:44'),
	(8, 10001, 108, '全年pro会员', '抽奖30次后解锁', 5, 5, 0.0010, 'rule_lock', 7, '2025-11-22 21:03:10', '2025-11-23 17:14:47'),
	(9, 10002, 105, '10次高级模型对话【Claude4.5sonnet/Gemini3】', '抽奖3次后解锁', 400, 400, 40.0000, 'rule_lock', 2, '2025-11-26 20:15:11', '2025-11-26 20:19:30');

-- 导出  表 aurora-marketing.strategy_rule 结构
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='策略规则表';

-- 正在导出表  aurora-marketing.strategy_rule 的数据：~10 rows (大约)
INSERT INTO `strategy_rule` (`id`, `strategy_id`, `award_id`, `rule_type`, `rule_model`, `rule_value`, `rule_desc`, `create_time`, `update_time`) VALUES
	(1, 10001, 101, 2, 'rule_random', '200,1000', '随机token数', '2025-11-22 21:05:25', '2025-11-22 21:14:10'),
	(2, 10001, 102, 2, 'rule_random', '1,1000', '随机积分数', '2025-11-23 17:17:56', '2025-11-23 17:18:09'),
	(3, 10001, 105, 2, 'rule_lock', '3', '抽奖3次后解锁', '2025-11-22 21:09:49', '2025-11-23 17:18:52'),
	(4, 10001, 106, 2, 'rule_lock', '3', '抽奖3次后解锁', '2025-11-22 21:11:08', '2025-11-23 17:18:55'),
	(5, 10001, 107, 2, 'rule_lock', '10', '抽奖10次后解锁', '2025-11-22 21:11:34', '2025-11-23 17:18:57'),
	(6, 10001, 108, 2, 'rule_lock', '30', '抽奖30次后解锁', '2025-11-22 21:12:01', '2025-11-23 17:19:00'),
	(7, 10001, 107, 2, 'rule_luck_award', '200,10000', '随机token兜底', '2025-11-22 21:13:56', '2025-11-23 17:19:02'),
	(8, 10001, NULL, 1, 'rule_weight', '30:103,104,105,106', '抽奖次数满30自动出', '2025-11-22 21:18:16', '2025-11-24 15:15:40'),
	(9, 10001, NULL, 1, 'rule_blacklist', '100:user001,user002', '黑名单用户100 token', '2025-11-22 21:23:46', '2025-11-25 14:37:58'),
	(10, 10002, 105, 2, 'rule_lock', '3', '抽奖3次后解锁', '2025-11-26 20:16:13', '2025-11-26 20:19:44');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
