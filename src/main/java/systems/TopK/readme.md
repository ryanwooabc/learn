# 0000-system-top-k
- https://github.com/donnemartin/system-design-primer/tree/master/solutions/system_design/sales_rank#design-amazons-sales-rank-by-category-feature

## Functional Requirements
- Spotify
- gather sales data efficiently
- list top sold products by category in the last week

## Non-functional Requirements
- high reliability/scalability/availability
- low latency

## Capacity Estimation
- 70K product sold in the last week
- 5 categories
- top 10

## Database Schema
- products(id, name, link ...)
- categories(id, name)
- sales(id, productId, amount, timestamp)
- accumulates(id, productId, amount, timestamp)
- tops(id, categoryId, productId, sales)

## System API
- String getPopularProducts(token, categoryId, count)

## High-level Design

- MapReduce
- SalesDB ---> MQ1 ---> AccumulateServiceN --> AccumulateDB --> PopularityServiceN ---> Cache ---> AppSvrN --> WebServer --> LB --> User
-          +-> MQ2 -+
- MQ: store sales records
- PopularityService: consume MQ, calculate popular products for each category
    - schelue at free time

- Client -> LB -+-> MusicService ---> Kafka <--- TopKGenerator
-               |                                      |
-               +-> TopKService -------------------> Cache ---> TopKDB

## Detail Design

### TopK in Data Stream
- HashMap + Heap
- CountMin Sketch + Heap

### AccumulateService
- consider only one category
- PriorityQueue + Sliding Windows + Prefix Sum
- (P1, D1), (P2, D1), .... (P1, D8)
- when receving new sales records, it will update AccumulateDB

### PopularityServiceN
- select amount1 - amount1 from select amount2 from accumulates where timestamp = current_day,
  select amount1 form accumulates where timestamp = current_day - 7d;
- populate into cache with TTL

## Reliabity / Availability / Scalability
- Replicate: MQ, AccumulateDB, Cache
- Cluster: AccumulateService, PopularityService, AppSvr

## Enhancement
- Security
- Telemetry, health, analysis
