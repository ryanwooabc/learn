# 0000-system-twitter
- why this feature?
- follow up? does it make sense?
- susbicius accounts/activity
- push notification to app or APN
- follow up, ask the requirement / definition, let's summarize


## Functional Requirement
- which one do we need to cover
- 200M DAU
- follow other users
- post a short 140-character message
- tweets may contain image or video
- user's timeline for following
- mark tweets as favorites

## Non-functional Requirement
- high reliability, cannot lost tweets
- high availability, can always post and watch timeline
- high scalability, support 1M, 10M, 100M users
- low latency, see timeline in 200ms

## Extended Requirement
- search tweets
- comment tweets
- hot tweets
- notification for following
- following recommendation
- TODO: moments
- TODO: tag users

## Capacity Estimatioin
- 1B users, 200M DAU
- 100M new tweets / day
- QPS: 100M / 24 / 3600 = 1K Write
- Storage: 100M * (280 + 30) = 30 GB / day
- one photo 200KB every 5, one video 2MB every 10 tweet
- Write: 100M / 5 * 200K + 100M / 10 * 2MB = 20TB / day
- Inbound: 20TB / 24 / 3600 ~= 200 MBps = 1.6 Gbps
- visit self timeline twice and 5 followings / day
- Read: 200M * (2 + 5) * 20 tweets = 28 B / day
- QPS: 28 B / 24 / 3600 ~= 30 B / 10^5 = 300K Read
- look at every phone, watch every 3rd video
- Outbound: 28B * (280 bytes + 200 KB / 5 + 2 MB / 10 / 3) / 24 / 3600 ~= 30B * 100KB / 10^5 = 30 GBps = 240 Gbps
- one user has 200 following
- TODO: user data, follows, favorites

## System API
- String uploadMedia(token, content)
- String tweet(token, content, location)
- token: rate limiter
- return: JSON with TwitterID/URL or HTTP errors

## Database Design
- users(id int, lastName vchar(64), firstName, email, birth, lastLogin, createdAt)
- tweets(id, userId, content, lat, lon, timestamp, favorites)
- follows(followerId, followeeId)
- favorites(userId, tweetId, timestamp)
- TODO-Instagram: SQL vs NoSQL

## High-level Design
-                              +-> UserDB
- Client ---> LB ---> AppSvrN ---> AggregatorN ---+---> TweetDB
-                                                 +---> CacheN
-   											  |       |
-    											  +---> ObjectStorage

## Detail Design
- Timeline generation, see Facebook Newsfeed

## Sharding
- high scalability / low latency
- by UserID, ConsistentHash
    - Pros: low latency for general users
    - Cons: high load for hot users, uneven data distribution
- by TweetID
    - Pros: even load
    - Cons: more latency
- by CreatedAt
    - Log(3600 * 24 * 365 * 50) ~= 10^5 * 4 * 10^4 = Log(4 * 10^9) = 31
    - 2^16 = 64K > 1K, 32 bits + 16 bits, 60 bits for 100 years with ms
    - Pros: find latest tweets, timeline generation read is substantially quciker

## Caching
- LRU for 20%
- 100GB for last 3 days without image or video, one server but with replicate
- <UserID, DoublyLinkedList<Tweet>>

## Load Balancing
- between client and appsvr
- between appsvr and TweetDB
- between aggregator and TweetDB
- Round Robin to intelligent LB

## Monitoring
- how many tweets per day/second, daily peak
- timeline delivery stat
- timeline refresh latency

## More Requirement
- TODO-Instagram: pre-generate timeline with ranking
- comments

## Terminology
- take a hit,受到影响
- recency
- constrast to
- epoch
- off-the-shelf
- substaintial,实质的
- Round Robin
- crucial
- constantly
- instant insight