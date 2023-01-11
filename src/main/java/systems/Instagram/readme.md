# 0000-system-instagram

## Functional Requirement
- upload / view/ share images
- like / dislike / comment others' images
- follow other users
- mark images as favorites
- generate newsfeed for a user's following
- notification for new feed

## Non-functional Requirements
- high reliability/availability/scalability
- low latency, e.g. see newsfeed (first page) in 200 ms

## Other Requirements
- auth
- search image with image title

## Capacity Estimation
- 1B users, 10M DAU
- upload 1 image per day
- 10M images / day, 10M / 24 / 3600 = 100 images / second, Write QPS
- 100 iamges * 200 KB = 20 MB / s
- Inbound: 160 Mbps
- write/read ratio = 1 : 200
- Outbound: 32 Gbps, read heavy
- Storage: 10M * 200 KB * 365 * 5 = 1M * 2M * 2K = 4 PB

## System API
- String upload(token, title, description, lat, lon, data)
    - return JOSON with image ID, URL, HTTP errors
- String view(token, imageID)
    - return JSON with image title, description, creator and other infos
- String like(token, iamgeID)
- String dislike(token, iamgeID)
- String follow(token, followeeID)
- String comment(token, imageID, content)
- String newsfeed(token, count = 20, pageToken = null)
    - return JSON [{imageID: '', imageURL: '', creator: '', location: '' }]

## Database System
- users(id, lastName, firstName, email, phone, addr, birth, createdAt, lastLogin)
- follows(id, followerId, followeeId)
- images(id, url, title, desc, lat, lon, creator, likes, dislikes, uploadTime)
- comments(id, imageId, userId, content, timestamp)
- User: ID 4 + last 20 + first 20 + email 32 + birth 4 + created 4 + login 4 = 88 bytes * 1B = 88 GB
- Image: 10M * 365 * 5 * (id 64 + user 4 + url 256 + lat 4 + lon 4 + time 4) ~= 10M * 2K * 300 = 6 TB
- Follow: 1B * 500 * 8 bytes = 4 TB

## High-level Design

- Client1 ---(upload)-+-> LB ---> AppSvrN --+--> ImageStorageN
- Client2 ---(view)---+                     +--> MetadataDB/R

- https://docs.google.com/drawings/d/1TSSp2Q-_ZMDMlXgD5-8InWsdSy4u1BtB2_JvFpGWXWg/edit
- Client: upload images, download hot images from CDN
- LB: between client and appsvr
- Application Server Cluster: serve HTTP request, store/read data from User/Follow/Image/Comment DB and Image Storage
- UserDB: RationalDB, 1B users
- MetadataDB: RDBMS with join
- MetadataDB: NoSQL like Cassandra with replicates
    - additional table to store <UserID, Set<PhotoID>>
    - <UserID, Set<Following>>

- FollowDB: GraphDB, 1B * 1B followingd
- Image/CommentDB: KV Store, no need CRUD
- ImageStorage: ObjectStore vs FileSystem, HDFS or S3
- CDN: push hot related image to CDN near a city for an image about news

## Detail Design

### Newsfeed
- Pull
    - get all following for a user, FollowDB
    - get all latest iamges of these following, ImageDB
    - aggregrate and sort result, AppSvr
    - return result to client with pagination, appsvr
    - first return the latest 20 images with pageToken(oldestImageTimestamp)
    - save token in client, get next 20 images before the last oldestImageTimestamp
    - Pros: get the latest iamges
    - Cons: higher latency
    - set image read, filter next time
- Push
    - a user uploads a image
    - find ID of followers
    - push image id to MQ of each user
    - follower can get newsfeed from MQ/Cache
    - Pros: low latency
    - Cons: not work for celebrity with 100M followers
- Hybrid
    - push for general users
    - pull images for celebrities

### Sharding
- by UserID
    - Cons: high load for hot user, data distrubuted unevenly
    - Pros: generate ImageID with Sharding Server
- by ImageID
    - Pros: handle hot user
    - dedicated 2 database servers to generate incremental ID
    - TODO: key generation schema
- virtual partion for data migration or upgrade
- Index ImageID to find the latest photos quickly

### Cache / CDN
- MetadataDB


### Reliability and Redundancy

## Terminology
- practically
- crucial
- mechanism
- eviction
- discard
- dictate




















