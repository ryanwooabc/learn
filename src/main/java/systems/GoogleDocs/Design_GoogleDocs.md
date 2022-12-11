# Design GoogleDocs
- https://www.educative.io/courses/grokking-modern-system-design-interview-for-engineers-managers/gkBKz2WL9Bk

## Functional Requirement
- pure text
- collaborate editing
- online/offline doc sync
- view/restore edit history
- TODO: comments

## Non-functional Requirement
- high reliablity: only one state per doc
- high availability: view and edit docs anytime
- high sacalibity: 100M documents
- low latency: change real time
-
## Capacity Estimatioin
- 100M DAU, 100M documents, 5 collaborators / doc, 100 changes / user day
- 100M * 10% = 10M WebSocket Conns
- 100M * 5 * 100 / 24 / 3600 = 580K QPS
- 100M * 100KB = 10TB

## System API
- SEND /operations
```json
  {
    "docId": 123,
    "userId": 456,
    "baseVersionId": 1,
    "operations": [ {
      "type": "insert",
      "pos": 3,
      "char": "a"
    }]
  }
```

## Database Design
- operations(id, docId, versionId, operation, timestamp)
- states(id, docId, content)

## High-level Design

### Git vs Google Docs
- Git: manual commit and resolve
- GoogleDocs: auto & real time as known sequence

### Operational Transformation
- o --> a ---+
- |          |
- b          b'
- |          |
- +---> a' -> both last status are the same
- Client send start version ID & transform
- wait for server response
- Server send final version ID & transform to Client
- OT may be a character typing, cut or paste
- RabbitMQ/ZeroMQ < Kafka
- WebSocket, Stream API

### Multi Clients = N * (Server + Client)
- introduce a centralized server

### Diff Sync & CRDT / Event Passing

## Detail Design


## Fault Tolerance
- Server: can reboot directly, only based on known states
- Operation MQ stored on Disk

## Monitoring
- Operation Queue Health
- Document Server Load
- Ongoing Connections (WebSocket)

## Teminology
- communtative,交换律

## References
- What’s different about the new Google Docs? Tuesday, May 11, 2010
- https://drive.googleblog.com/2010/05/whats-different-about-new-google-docs.html
- What’s different about the new Google Docs: Working together, even apart? Tuesday, September 21, 2010
- https://drive.googleblog.com/2010/09/whats-different-about-new-google-docs_21.html
- What’s different about the new Google Docs: Conflict resolution? Wednesday, September 22, 2010
- https://drive.googleblog.com/2010/09/whats-different-about-new-google-docs_22.html
- What’s different about the new Google Docs: Making collaboration fast? Thursday, September 23, 2010
- https://drive.googleblog.com/2010/09/whats-different-about-new-google-docs.html

## Requirement
- 保持两份以上副本的实时同步
- differential 差异化的同步算法
- 可扩展性，容错，响应化的协同编辑，可靠性

## 传统方法
- 所有权，事件传送，三路合并，概念上简单，但有缺点 drawbacks
- 加锁访问比较简单，微软Word访问共享网络磁盘上的文件，第一个可写，其他只读
- 一种改进的方法是对文档的局部进行加锁和释放，但是不能解决紧密的合作
- 事件传递抓取用户所有的操作，通过网络映像给其他用户，基于操作转换的算法，无法解决现代用户接口的复杂性
- SVN采用三路合并，客户端发送内容给服务器，服务器执行三路合并，然后发给其他客户端

## 生词
- ill-suited
- result in fork
- not naturally convergent
- 13G
- 1234-5678-9abc-d
- 1234-5678-9abc-de
- 0123-4567-89ab-cd

## Questions
- client-server or peer-to-peer
























