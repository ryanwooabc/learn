# Design Rate Limiter

## Functional Requirements
- throttle requests based on API, UserID or IP
- accurately limit excessive requests
- return 429 too many requests if reaching the limit
- hard/soft/elastic throttling

## Non-functional Requirements
- distributed rate limiting
- scalability
- high availability
- low latency
- handle exception
- fault tolerance

## Capacity Estimation
- (8 bytes UserID + 2 bytes epoch minute + 2 bytes Counter + 20 bytes hash overhead) * 1M = 32MB # Fixed Time Window
- ((8 bytes UserID + (4 bytes epoch + 20 bytes overhead) * 500 + 20 bytes overhead) * 1M = 12GB  # Sliding Window
- (8 bytes UserID + (4 bytes epoch + bytes Counter + 20 byters) * 60 + 20 overhead) * 1M = 1.6GB

## System API

## Database Design
- requests: <UserId, <Count, Timestamp>> # Fixed Time Window
- Requests: <UserId, TreeSet<Timestamp>> # Sliding Window

## High-level Design

- Client ---> RateLimiter ---> APIServiceN
  +-> RateCache

- API Gateway: a fully managed service, rate limiting, SSL termination, IP whitelisting, static content servicing
- Token Bucket, by Amazon & Stripe
  - pre-defined capacity, token are put in the bucket at preset rates periodically
  - no more tokens if bucket full
  - each request consumes one token, will be forwarded if token enough
  - the request is dropped when no token left
  - Pros: easy to implement, memory efficient, burst of traffic allowed
  - Cons: challenging to tune two parameters bucket size and refill rate
- Leaking Bucket, TODO
- Fixed Window Counter
  - the algorithm divides timeline into fix-sized time windows, and assign a counter for each window
  - Pros: memory efficient, easy to understand, reset available quota at the end of a unit time window
  - Cons: a burst of traffic at the edge of time windows may cause more requests than allowed
- Sliding Window Log
  - fix the issue of Fixed Window Counter
  - create a log with timestamp for each request
  - clean outdated logs with a time window
  - count logs and reject requests if count more than allowed
  - Pros: accurate, guarantee request no exceed in any rolling window
  - Cons: logs of rejected requests are still stored in memory
- Sliding Window Counter
  - hybrid approach to combine Fixed Window Counter and Sliding Window Log
  - requests in current windows + requests in previous windows * overlap percentage of rolling window and previous window
  - Pros: smooth out traffic spikes as the rate is based on the averateg of previous window
  - Cons: approximation of the actual rate, 0.003% more is wrongly allowed in Cloudflare, TODO-Qi

- Rate Limit Rules, Lyft
  - domain: messaging
  - descriptors:
    - key: message_type
          value: marketing
          rate_limit:
          unit: day
          request_per_unit: 5

- Exceed Limit
  - return HTTP 429 too many requests
  - header: X-Ratelimit-Remaining, X-Ratelimit-Limit, X-Ratelimit-Retry-After


## Detail Design
```java
-                         +-------> RuleCache ----> WorkerN ---> RuleDB
- Client ---------> RateLimiter ----(Success)---> APIServiceN
-   ^                 |   +-----> RateCache
-   |                 |
-   +---HTTP 429------+------> RequestDrop
-                     +------> MessageQueue
```

- Worker: pull from RuleDB and populated into RuleCache
- RateLimiter: load rule from RuleCache, fetch counter and last request timestamp from RateCache
- Distributed Rate Limiter
  - Distributed KV Store, centralized data stores
  - TODO:

- Sharding, Consistent Hash
- Caching, and dump into permanent storage

## Enhancement
- monitoring, rule and algorithm are efficient

## Terminology
- throttle，节流阀
- get buy-in
- illustrate
- preset
- vary
- burst of traffic
- Stripe
- Spike
- substantial
- epoch
- overhead
- reduce memory footprint