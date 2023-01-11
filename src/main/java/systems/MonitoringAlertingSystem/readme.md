# 0000-system-monitoring

## Functional Requirement
- event count
- event time series
- event filter
- alert

## Non-functional Requirement
- high reliablity, correct data, purge old data
- availability, access any time
- sacalibity, high EPS
- low latency, one minute delay

## Capacity Estimatioin

## System API

## Database Design

## High-level Design

- RawEvent -(LocalDaemon)-> MachineLevelAggr -(Kafka)-> ServiceLevelAggr --> MatricStore <-- MetricConsumer

## Detail Design

### EventStore
- Elasticsearch

### Counter Event

### Duration Event
- average response time
- percentile by histogram

## Teminology