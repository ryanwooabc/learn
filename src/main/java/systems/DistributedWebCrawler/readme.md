# 0000-system-web-crawler

## Purpose
- search engine indexing
- web archiving
- web mining
- web monitoring

## Functional Requirements
- 1B pages / month
- HTML only, store for 5 years
- HTTP/HTTPS only
- only one web site
- thousand of machines in botnet
- minimize traffic among machines
- crawl once per pages
- a machine may be down at anytime
- a machine should not have too high load
- TODO: storage

## Non-functional Requirements
- Scalability
- Robustness, handle exception
- Politeness, follow robot rules and not overload a web server
- Extensibility, support new content/format

## Capacity Estimation
- 1B pages / month
- 10^9 / 30 / 24 / 3600 = 10^4 / 30 = 300 QPS
- Peak QPS = 600
- Storage: 1B * 500K * 12* 5 = 30PB
- Bandwidth: 600 * 500K / s = 300MBps = 2Gbps

## High Level
```java
-                               DNS Resolver                            ContentDB  
-                                   ^                                        ^  
-                                   |                                        |  
- SeedURL ---> URLFrontier ---> HTML Downloader ---> Content Parser ---> Content Seen?
-                   ^                                                        |  
-                   |                                                Link Extractor  
-                   |                                                        |  
-                   |                                                    URL Filter  
-                   |                                                        |   
-                   +--------------------------------------------------- URL Seen?  
-                                                                            |  
-                                                                         URL DB  
```java

- Seed URL: starting point
- URL Frontier: URL Queue, Kafka? 参考文献？Distribute to multiple servers
- URL Filter: filter content types, blacklist websites, user customized, domain, prefix, protocols
- Content DB, check sum in memory, 1B * 8 bytes = 8GB, LRU cache then DB
- URL DB, check sum in memory, 1B * 8 bytes = 8GB, LRU cache then DB
- DNS Resolver: cache to avoid repeated requests, Map<HostName, IP>

## Solution 1
- C&C = Command and Control Server
- single point failure globally

## Solution 2
- Crawler Cluster to share crawl list and history
- multiple command and control servers?
- local command and control server for each crawler group
- single point failure in each group

## Solution 3
- Consistent Hashing Botnet Crawler
- more nodes on a super node
- super node is a set, 保证数据备份足够多？
- OutboundQ vs Failover
- 等待恢复 vs 另选节点

## System API
- int download(string url)

## Database Design
- pages(id, linkId, checksum, downloadTime)
- urls(id, link, lastDownloadTime, updateFreq)

## Cache
- urls.checkSum, 1B * 8 bytes = 8GB
- TODO: bloom filter
- 一个URL用N个哈希计算出不同的BitIndex
- 如果这个URL存在的话，那么这N个BitIndex都应该为1
- 但是一个URL不存在，所对应的N个BitIndex也可能都为1
- False Negative 假阴性不可能
- URL哈希的BitIndex没标记，那就肯定不在，不存在假阴性
- URL没有访问，有可能被标记成访问过，从来不去爬取，可以扩大位向量大小来解决
- URL访问过了，那就肯定能检测到，不可能出现假阴性

## Detail Design
- DFS, reuse the connection for the same site, avoid handshake, Stack应该不支持
- BFS, FIFO
- Path-ascending crawling
- Politeness Constraint: Delay for each Queue
- Mapping Table ---> Queue Router -+-> Q1 ---> Queue Selector -+-> Worker Thread 1
-                                  +-> Q2 -+                   +-> Worker Thread 2   
-                                  +-> Q3 -+                   +-> Worker Thread 3  
- Mapping Table <Host, Queue>
- Queue Selector: worker 1 : n queue
- Priority: PageRank
- InputURL -+-> Prioritizer ---> F1 -+-> Front Queue Selector ---> Back Queue Router -+-> Q1 -+-> Back Queue Selector -+-> Worker Thread 1
-                            +-> F2 -+                                    |           +-> Q2 -+                        +-> Worker Thread 2
-                            +-> F3 -+                             Mapping Table      +-> Q3 -+                        +-> Worker Thread 3
- Freshness, update history or priority
- Frontier: Queue/Kafka, Hybrid, majority on disk, buffers in memory
- EnqueueBuffer -> Disk -- DequeueBuffer
- robot.txt, first check robots exclusion protocol, put in Map<host, rule> in memory
- Snapshot and restore from checkpoint

## Performance
- partition URL DB
- cache DNS
- locality, geographically close to website hosts
- short timeout
- checksum to avoid duplicates
- max URL length for spider trap
- data noise like ads, spam URLs


## Extensibility
- Content Seen -+-> Link Extractor
  +-> PNG Downloader
  +-> Web Monitor

## Fault Tolerance
- ConsitentHash: replace a dead host and distribute load
- save Frontier queue into disk
- shift queue file to another host

## Data replication and partition
- based on hostname

## Enhancement
- Telemetry
- Analytics

## Questions:
- How to handle URLs constructed by JavaScript

## Hints
-    KB   MB   GB   TB   PB
-    K    M    B

## Terminology
- for the sake of, 为了什么目的、好处
- courteous
- appropriate
- canonical hostname
- off-limits
- canonical form
- probabilistic