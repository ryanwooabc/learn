# 0000-system-chat-app
- trade off
- https://systeminterview.com/design-a-chat-system.php
- https://www.educative.io/courses/grokking-the-system-design-interview/B8R22v0wqJo

## Functional Requirements
- one-on-one chat apps like FB Messager, WeChat and WhatApp
- group chat like game chat, Discord, Slack
- instant message with text, image and video are optional
- both one-on-one and group chat
- both mobile app and web
- multiple device support
- online status indicator
- push notification
- store chat history for 5 years
- group member limit 100
- weak: service, database, storage, tables
- hire: sharding, WebSocket, Push Notification
- strong: channel for large group chat, online status udpate
- TODO: QPS, delivered, sort by time

- group chat
- sent/delivered/read recipts
- online/last seen
- image sharing
- chat history store
- one to one chat

## Non-functional Requirements
- high availability
- low delivery latency
- not encryption required

## Capacity Estimation
- assueme user/friend/group DB already existing
- 500M DAU * 40 meesages * 100 bytes = 2TB / day
- Storage: 2TB * 365 * 5 years = 4PB / 5 years
- Network: 2TB / 3600 / 24 = 20MBps = 160Mbps for upload and download
- Connection: 1M concurrent users * 10K / connection = 10GB Memory
- Connection: 500M / 50K = 10K ChatServers

## System API

## Database Design
- messages(id, fromUserId, toId, toType, content, delivered, timestamp)
- TODO: sort by message ID from Distributed ID Generator

## High-level / Detail Design
- HTTP Polling, the client periodically ask the server if there're messages avaiable
- Long Polling, the client holds the connection open until a new message or timeout
    - sender and receiver may not connect to the same chat server
    - server has no good way to tell if client is disconnected
    - cost is too high as long polling still makes periodical after timeout
- Web Socket, send asynchronous updates from server to client
    - initiated by client, bi-directional and persistent

- ServiceDiscovery: ZooKeeper base on geo location and server capacity

## Detail Design
- Stateless / AppSvr: signup, login, user profile, friends, group,
- Stateful / WebSocket: Client, CharServer, persistent connection
- ServiceDiscovery: coordinates closely with chat servers to avoid overload
- ThirdParty / Push Notification: notify when app not running
- ChatDB / KV: horizontal scaling, low latency like HBase for FB and Cassandra for Discord
```java
- client --+-- WebSocket --+---> PresenceServer -------> StatusDB
-          |               +-+-> ChatServerN ---> ChatDB <--- ChatService
-          |                 ^        |                          ^ 
-          |      DiscoveryService    + --> MQ --> PN            | 
-          |                 |                                   |
-          +-- LB --> AppServer ---------------------------------+
```

- ServiceDiscovery
    - Client login
    - LB forward traffic to AppSverver
    - DiscoveryService find the best Chat Server
    - Client connects to the Chat Server

```java
-                +---> ID Generator	              +---> Notification
- User1 -----> ChatServer1 ---> MessageSyncQueue -+---> ChatDB
-                                                 |
- User2 -----> ChatServer2 <----------------------+
```

- One-on-one Messages
    - User1 sends Message1 to ChatServer1
    - ChatServer1 generates ID1 for Message1 with ID Generator
    - ChatServer 1 sends an acknowledgement to User1
    - ChatServer1 sends Message1 to MQ
    - Message1 is stored in ChatDB
    - Send Notification to User2 if User2 is offline
    - Forward Message1 to ChatServer2 if User2 is online
    - ChatServer2 forwards Message1 to User2
    - User2 sends acknowledgement to ChatServer2
    - ChatServerA send delivery success result to User1

- Client1 -+-> ChatServer1 ---> ChatDB
- Client2 -+

- Message Synchronization
    - Maintain latestMessageId in Clients
    - Retrieve and send messages which ID is newer than above
    - TODO: Initiated by Client or Chat Server?
    - TODO: Will Clients connect to different Chat servers?

```java
- User1 ---> ChatServer1 -+-> MQ1 ---> ChatServer2 ---> User2
  +-> MQ2 ---> ChatServer3 --->User3
```
        
- Group Chat
    - each group member has a MQ
    - Message1 will be copied to MQ of other group members
    - limited group members

```java
- User1 ---> WebSocket ---> StatusServer -+-> StatusDB
-        +-> Logout ---> AppSvr ----------+
```

- Online Status
    - login, connect to StatusServer, get status list of all friends
    - <UserId, <Status, LastSyncTime>>
    - It's too ofter to update status when users disconnect/reconnect
    - Better to use TTL
    - get status for friends in viewpoint
    - TODO: use REST for both login and logout as no need bi-direction

- Online Status Fanout
    - For small group, 1-N channels, pub-sub model, real-time WebSocket
    - For large group, fetch member status list when entering the group

## Scalability & Performance
- ChatServer sharding by UserID
- Sharding by MessageID, difficult to retrieve all messages for a user, not adopt
- Cache the latest messages
- Load Balancer between Client and ChatServer, or ServiceDiscovery


## Terminology
- nail down, 确定，大头钉，指甲
- it's vital to agree
- the right recipient
- initiated by
- initialize
- initially
- trivial
- precious server resource
- costly
- progression
- drawbacks
- straightforward
- sit behind
- route requests
- monolithic
- crucial
- big flag
- deal breaker
- desirable
- tolerate
- enormous
- reliable
- ascertain
- criteria
- a major flaw
- arbitratily chosen
- orchestrating all communications
- intended user
- asynchronous
- adopt
- failover TCP connection
- manufacturer