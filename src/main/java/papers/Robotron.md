# 0000-robotron-read.md
- CCS Concept, ACM Computing Classification System，网络管理

## 1 简介

## 2 网络及其用例

## 2.1 存在点

## 2.2 数据中心

## 2.3 主干网，1001

## 3 Robotron 综述

## 4 FBNET - 网络建模

### 4.1 数据模型

#### 4.1.1 对象，值，和关系

#### 4.1.2 需求还是继承

### 4.2 接口

#### 4.2.1 读接口

#### 4.2.2 写接口

### 4.3 架构和实现

#### 4.3.1 存储层

#### 4.3.2 服务层

#### 4.3.3 扩展性和可用性

## 5 管理生命周期

### 5.1 网络设计

#### 5.1.1 存在点和数据中心

#### 5.1.2 主干路由器

### 5.2 生成配置

### 5.3 部署

#### 5.3.1 初始准备

#### 5.3.2 增量更新

### 5.4 监测

#### 5.4.1 被动监测

#### 5.4.2 主动监测

#### 5.4.3 监测配置

## 6 使用统计

### 6.1 FBNet 模型

### 6.2 设计变更

### 6.3 配置变更

### 6.4 监测使用

## 7 Robotron的发展

## 8 经验和未来工作

## Facebook Robotron Part 3, 1003

## 图-1 Facebook 网络总览
- 用户通过互联网，访问存在点
- 存在点通过主干路由，和数据中心通信

## 图-2 存在点集群
- 互联网连接对点路由
- 对点路由通过主干路由互联
- 对点交换机连接对点路由
- 机架定交换机再连接对点

## 图-3 Robotron系统概览
- 网络设计，配置生成，部署，监测
- Facebook 网络，数据库

## 图-4 对点交换机和对点路由器的端口映射
- 对点交换机a的10G 以太网卡 1/1
- 连接到对点路由的以太网卡 2/1

## 其他阅读
- https://www.zhihu.com/column/p/23158808
- 读接口要求指定必要字段，和对象过滤查询
- 写接口允许修改多个兑现
- 使用 Django 和 MySQL 实现
- 服务层使用 Thrift
- 写入到 MySQL 的 主节点，然后读取副本
- 如果副本读取失败，Robotron会禁用这个副本，并将流量重定向到其他副本


## 术语
facilitate, 促进  
practice, 实践  
Robotron, 机器人  
fashion, 时尚  
reduce effort, 减少努力  
intent, 意图  
deviate from, 从。。。偏离  
destiny, 命运  
circuit turn-up, 电路开通  
migration, 移民  
device privisioning, 设备配置  
value, 价值  
judicious, 明智的  
prerequisite, 先决条件  
routing protocol, 路由协议  
naturally, 自然  
high-profile incident, 高调事件  
evolve, 发展  
the field of network management, 网络管理领域  
published principle, 公布的原则  
data plane, 数据平面  
tranlate high-level intent, 翻译高级意图  
multitude, 苠  
heterogeneous, 异质  
migrate a circuit, 迁移电路  
BGP session, BGP 会话  
drain, 流走  
undrain, 不排水  
point of presence, POP, 存在点，POP  
backbone, 骨干  
end-hosts, 终端主机  
configuration as code, 配置即代码  
constraint, 约束  
continuous, 连续  
vendor neutral, 供应商中立  
over time, 随着时间的推移  
simultaneously, 同时  
undergone, 经历  
involve, 涉及  
coupled parameter, 耦合参数  
autonomous system, 自治系统  
laborious, 费力的  
despite, 尽管  
unify, 统一  
capability, 能力  
vary, 各不相同  
goal, 目标  
minimize, 最小化  
codify, 编纂  
point-to-point, 点对点  
circuit, 电路  
last line of defense, 最后一道防线  
commit, 犯罪  
employ continuous monitoring, 使用持续监控  
tremendous, 巨大的  
evolved, 进化的  
strive, 努力  
bog down, 停滞  
minimize manual, 最小化手动  
tens of thousands ofcore architecture, 数以万计的核心架构  
challenge, 挑战  
moticate, 暗示  
model-driven, 模型驱动  
heterogeneous, 异质  
statistics, 统计数据  
point-of-presense,POP, 存在点，POP  
backbone router,BB, 骨干路由器,BB  
perspective, 看法  
prospective, 预期  
PSW, switch, PSW，开关  
top-of-rank switches, TOR, 顶级交换机，TOR  
latency, 潜伏  
BGP, BGP  
comprehensive, 综合的  
comprise, 包括   
