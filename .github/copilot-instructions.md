# AuroraMarketing 项目 Copilot 指令

## 项目概览

这是一个基于 DDD（领域驱动设计）架构的开源营销平台，支持抽奖策略和营销活动管理。

## 架构规范

### 模块结构

项目采用 Maven 多模块架构，需严格按照以下模块职责进行开发：

- **AuroraMarketing-api**: API 定义和响应对象，不包含业务逻辑
- **AuroraMarketing-app**: 应用启动层，包含配置和启动类
- **AuroraMarketing-domain**: 核心业务领域，包含实体、聚合、仓储接口和领域服务
- **AuroraMarketing-infrastructure**: 基础设施层，实现仓储接口和外部服务调用
- **AuroraMarketing-trigger**: 触发层，处理 HTTP 请求、定时任务和事件监听
- **AuroraMarketing-types**: 通用类型定义，包含枚举、常量和异常

### DDD 设计原则

#### 领域层 (Domain) 规范

- **实体 (Entity)**: 以 `XxxEntity` 命名，包含业务标识和业务方法
- **聚合 (Aggregate)**: 以 `XxxAggregate` 命名，聚合实体和值对象，不直接调用仓储
- **仓储接口 (Repository)**: 以 `IXxxRepository` 命名，定义数据访问接口
- **领域服务 (Service)**: 处理复杂的跨实体业务逻辑

#### 基础设施层 (Infrastructure) 规范

- **仓储实现**: 继承领域仓储接口，处理数据持久化
- **PO 对象**: 以 `Xxx` 命名（如 `Strategy`, `Award`），对应数据库表结构
- **DAO 接口**: 以 `IXxxDao` 命名，使用 MyBatis 进行数据访问

## 其他

目前项目处于分步开发阶段，代码审核时无需过多考虑其他内容，有些内容后续会添加和完善，只需关注已有的代码内容。

**请从您的所有意见中筛选出小于10条的核心审查建议进行反馈。**
