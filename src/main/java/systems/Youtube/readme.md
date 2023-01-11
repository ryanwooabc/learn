# Design Youtube
- 大家请先自己绘制基础架构图以及进阶架构图  

# 推荐阅读
- http://highscalability.com/blog/2017/12/11/netflix-what-happens-when-you-press-play.html#:~:text=Netflix%20controls%20each%20and%20every,%2C%20TVs%2C%20and%20other%20devices.
- https://systeminterview.com/design-youtube.php
- https://medium.com/double-pointer/system-design-interview-video-streaming-service-e-g-netflix-or-youtube-design-adc2402e54a1

# 常见疑问
- 如何提高上传和下载的速度与效率？
- 如何进行动态扩容以及处理峰值情况？
- 有哪些可以改进网络连接速度/效率的方案？

# 0000-system-youtube
- https://blog.csdn.net/xiaozaq/article/details/109378302

## Functional Requirements
- upload/view/share/search videos
- count views/likes/dislikes
- add/view comments
- client: web, app, TV
- streaming video

## Non-functional Requirements
- high reliability/availablity/scalability
- low latency

## Not Required
- recommendation, popular / hot videos
- channel, subscription, newsfeed
- watch later, favorites, playlist
- authentication, autorization, encryption

## Capacity Estimation
- 1.5B users, 800M DAU, view 5 videos / day
- QPS: 800M * 5 / 24 / 3600 = 4 * 10^9 / 10^5 = 40K videos / sec
- upload/view ratio = 1:200,  40K / 200 = 200 videos / sec
- video average: 720p * 5 minutes = 1280 * 720 * 3 * 30 frames / s * 60 * 5 = 30 * 10^6 * 10^3 = 30GB?
- video average: 720p * 5 minutes * 5 types = 2.5 GB
- *** video average: 720p * 5 minutes * 5 = 10M * 5 * 5 = 250 MB
- Write: 200 video * 250 MB = 50 GB / sec
- Upload: 50 GB / 5 types = 10 GBps
- Download: 10 GBps * 200 = 2TBps

## System API
- String upload(String token, String title, String desc, String[] tags, bytes[] contents)
    - return { status: 202, url: "xxx" }
- String search(token, query, location, count, pageToken)
    - return [ { id: 1, title: "abc", url: "xxx", thumbnail, date, views } ]
- Stream play(token, videoId, offset, codec, resolution)

## Database Design
- videos(id, title, desc, creatorId, size, thumbnail, url, likes, dislikes, views, createdAt)
- comments(id, videoId, userId, content, timestamp)
- users(id, type, lastName, firstName, email, addr, birthDate, details)

## High-level Design
```java
-                                                                 UserDB
-                                                                   |
- Client(upload/search/view) -> LB -> WebServer ----------------> AppSvr ------+-------------+
-     ^                                ^    MetadataDB/Cache <---+  |          |             |
-     |                                |    ThumbnailDB/Cache <--+- +   ProcessingQueue     MQ
-    CDN <--------------- ViewImageCache <------ VideoStorage <--+  |          |             |
-                                                                 Encoder -----+-------------+
```

- ProcessingQueue: to upload video
- Encoder: different resolution, file type
- Thumbnail: every 1 seconds ?
- ObjectStorage: blob, binary large object
- UserDB
- MetadataDB: video title, description, url, views, likes, dislikes, comments

## Detail Design

### Video Storage
- distributed file storage system
- replication for read-heavy
- upload to primary and sync to seconday

### Thumbnail Storage
- KV store, <VideoID:Timestamp, content>
- cache in memory, LRU

### Upload Video
- split to chunk, support resuming upload
- GOP alignment, Group of Picture
- upload centers close to users
- Safty: DRM, AES, Watermark
- TODO-S3: pre-signed upload URL

### Encode Video
- bit rates, formats, at most 4K
- notify uploader once encoding done

## Deduplicate Video
- waste video storage, cache, network, energe
- video matching algorithm
- skip low resolution or subpart of existing videos

## Stream Video
- MEPG-DASH, Moving Picture Experts Group, Dynamic Adaptive Streming over HTTP
- Apple HLS, HTTP Live Streaming
- Microsoft Smooth Streaming
- Adobe HDS, HTTP Dynamic Streaming

## Handle Errors
- upload failure
- malformated video
- encoding failure

## Reliability / LoadBalancer
- consistent hash
- elastic scaling
- replace down servers and distribute load
- minimize replication when server down

## Scalabilty / MetadataDB
- sharding with UserID
    - Pros: fast to find videos of some channel
    - Cons: too heavy for a hot user on a server, not evenly ditributed
- shareding with VideoID
    - cache metadata for hot videos

## Performance / Cache / CDN
- geographically distributed
- MetadataDB LRU, 20% hot
- CDN: distribute hot video geographically

## Enhancement

## Terminology
- segregate,分离
- deliberate,深思熟虑的
- stale，陈旧的
- widespread
- drawback,缺点，出口退税
- intertier
- complex technologies underneath the simplicity
- Youtube is enormous
- get buy-in,得到支持
- bunch of workers, 一群
- continuous
- contiguous