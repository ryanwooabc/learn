# Design Dropbox
- Google Drive, Dropbox, Microsoft OneDrive, Apple iCloud
- https://www.educative.io/courses/grokking-the-system-design-interview/m22Gymjp4mG
- https://www.algoexpert.io/systems/workspace/design-google-drive
- https://systeminterview.com/unlock.php
- https://www.youtube.com/watch?v=PE4gwstWhmc

## Functional Requirement
- upload/download/sync/share files
- see file revisions
- send notification for a file change
- optional: encryption

## Non-functional Requirement
- high reliability/availability/scalability
- low latency
- less bandwidth

## Capacity Estimatioin
- 50M registered users, 10M DAU
- Storage 50M * 10G = 500 PB
- upload 2 files / day, 500KB / file, 1:1 read to write ration
- Upload: 10M * 2 / 24 / 3600 = 200 QPS, Peak 400

- 100M DAU, upload 1 file, update 10 files / user * day
- update 100M * 10 / 24 / 3600 = 12K QPS
- upload 100M / 24 / 3600 = 1K QPS

## System API
- int upload(token, data, resumable)
- bytes[] download(token, path)
- JSON listRevision(token, path)

## Database Design
- users(id, name, createdAt)
- workspaces(id, userId, shared, createdAt)
- files(id, name, type, path, checksum, workspaceId, latestVersion, lastModifiedAt)
- versions(id, number, fileId, deviceId, lastModifiedAt)
- blocks(id, versionId, order)
- device(id, user, lastLoginAt)
- FileJournals(namespaceId, relativePath, journalId, blockList)

## High-level Design
-           +---> Notif <- MessageQueue --+
- Client ---+---> LB ------------------> AppSvr ---+---> MetadataCache ---> MetadataDB
-           |      |
-           +---> BlockServer ---> CloudStorage ---> ColdStorage

![ImageURL](https://github.com/ryanwooabc/learn/blob/main/systems/Dropbox/Design_Dropbox.png)

## Detail Design

### Storage Options
- File Storeage, Tree Hierachical Structure
- Block Storage, block ID, simple
- Object Storage, Object URL,

### BlockServer
- delta sync, only modified blocks
- compression, gzip or bzip3
- TODO: [1,2,3,4], [5,6,7,8], [9] -> U1: [1,2,3,x],[4],[5,6,7,8],[9]
- content addressable storage, (hash, block), SHA-256, max 4MB Chunk

### File History
- GoogleDoc: Change Log for history
- DropBox: File Journal for history, based on Block Storage

### Strong Consistency
- difference among clients is not acceptable
- cache and master are consistent

### Upload Workflow
- Upload Status: Client1 ---> AppSvr ---> MetadataDB ---> NotificationService
- Upload Content: Client1 ---> BlockServer ---> CloudStorage ---> AppSvr ---> MetadataDB ---> NotificationService

### Download Workflow
- MetaServer <- list(namespace: journalId) <- DL Client
- MetaServer -> (namespace, journal, path, blockList) -> DL Client

### Sync Conflicts

## Teminology
- narrow down
- synchronization
- guru
