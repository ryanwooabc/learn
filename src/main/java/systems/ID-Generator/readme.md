# 0000-system-id-generator

## Functional Requirement
- ID should be unique, numberical value only
- ID should fit into 64-bit and be ordered by date
- ability to generate over 10K ID / second

## Non-functional Requirement

## Capacity Estimatioin

## System API

## Database Design

## High-level Design

### Multi-master Replication
- K master server with auto_increment by K
- TODO: hard to scale with multiple data centers
- ID not created across multiple servers
- not scale well when adding or removing servers

### UUID, Universally Unique Identifier
- 128 bits, low probability for collusion
- Based on MAC, Timestamp, Random Number etc
- Pros: simple, easy to scale
- Cons: 128-bit too long, not incremental, non-numeric

### Ticket Server
- centralized single auto_incremental server by Flicker
- Pros: easy to implement, for small/medium scals
- Cons: Single point of failure

### Twitter Snowflake Approach
- sign 1 bit + timestamp 41 bits + datacenter 5 bits + machine 5 bits + seq 12 bits

## Detail Design
- timestamp 41 bits: 2^41 - 1 = 2 * 10^12 ms = 69 years
- sequence numnber: 2^12 = 4096

## Wrap up
- clock sync
- section length tuning
- high availability

## Teminology
- collusion
- collision
- inprison
- inspiring，激励，令人鼓舞的
- epoch

##