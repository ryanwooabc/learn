# 0000-system-distributed-kv
- component design, database design
- maintain cache, network configuration, lock
- memory configuration, distributed system-distributed-kv

## Functional Requirement
- put <Key, Value> pair
- get Value by Key
- delete(key)
- LRU
- key-value pair less than 10KB
- ability to store big data
- auto scaling
- tunable consistency

## Non-functional Requirement
- high reliablity/availability/sacalibity
- low latency

## Capacity Estimatioin
- (100K + 1M) / 100K = 11

## System API

## Database Design

## High-level Design

- CacheClient --(RPC)--> CacheProxy ---> AcceptorThread ---> Q --> WorkerThreadN
-                             |
-                        ZooKeeper(ConfigService)
- HashTable, Map + LinkedList
- LRU
- Cache Server, Memory Allocation, Slob1/1KB, Slot2/2KB

- single server with hash table, compress data, dump to disk
- CAP, Consistency, Avalability, Partition Tolerance
- CP: SQL, Bank, Finance, AP: NoSQL, Distributed System

## Detail Design
-                                +--- Node 6   +---> Node1
- Client ---> (write/read) ---> Node5 ---------+---> Node2
-                                +--- Node 4   +---> Node3

### Data partition
- consistent hash with virtual nodes
- auto scaling: add/remove servers depending on load
- heterogeneity: high-capacity servers are assigned with more virtual nodes

### Data replication
- next N virtual nodes, belongs to unique physical servers

### Consistency
- N replicas, W: write quorum, R: read quorum
- Quorum: the operation is successful only when receiving acknowledges from x replicas
- R = 1, W = N, optimized for fast read
- W = 1, R = N, optimized for fast write
- W + R > N, strong consistency
- W + R <= N, weak consistency, e.g. eventual consistency

### Inconsistency Solution - versioning
- resolved by client

### Handle Failure
- Gossip protocol
- each node increase heartbeat count periodically, and send to random nodes
- the member is considered as offline if the heartbeat is not increased for a certain period

### Handle temporary failure
- sloppy ruorum
- hint handoff

### Handle permenent failure
- anti-entropy protocol
- Merkle tree

###  Write Path

- Client --------------+---------------> MemoryCahce
-                      |                     |
-                   CommitLog             SSTable

### Read Path

- Client -------------------------------> MemeryCache
-                                            |
-            ResultData <--- SSTable <--- BloomFilter

## Replication
- across data center

## Teminology
- proportional, 成比例的，协调的
- inevitable,不避免的


## TODO-cache
- bucket level lock
- multi-bucket level
- page level
- slab level



