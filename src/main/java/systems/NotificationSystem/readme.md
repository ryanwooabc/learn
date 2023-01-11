# 0000-system-notification-system


## Funtional Rquirements
- types, App Push notification, SMS, Email
- real time
- devices: iOS, Android, Laptop & Desktop
- triggered by client App or scheduled
- opt-out users will not receive
- 10M Push notification, 1M SMS, 5M Email per day

## Non-functional Requirements
- Handle single point of failure
- Scalability
- Performance bottle

## System API
- boolean notify(deviceToken, aps = { alert: { title, body, action-loc-key}, badge });
- POST /sms/send { phoneNo: xxx, message: xxx }

## Database Design
- users(id, email, countryCode, phoneNo, createdAt)
- devices(id, userId, token, lastLoginAt)

## High Level Design

### Notification Type
- Provider ---> Apple Push Notification Services ---> iOS
- Provider ---> Google Firebase Cloud Messaing ---> Android
- Provider ---> SMS Service / Twilio / Nexmo ---> SMS

### Contact Info gathering
- user --> install/sign up --> load balancer --> application server ---> ContactInfoDB

### Notification sending/receiving
- Service1 -+-> Notification System N -+-> iOS Queue --> WorkerN --> APNS ---> iOS
- Service2 -+           |              +-> And Queue --> WorkerN --> FCM  ---> Android
- Service3 -+         Cache            +-> SMS Queue --> WorkerN --> SmsSvc -> SMS
-                       |              +-> EML Queue --> WorkerN --> EmailSvc -> Email
-                     MsgDB						
- ServiceN: MicroServices, cron jobs
- Notification System
    - provide API for service to send notification
    - basic validation
    - query database and form message
    - put notification to message queue
- 3rd-party services, extensibility
- user devices:
- Cache: user info, device info, notification template
- Message Queue: worker pull events and push to 3rd-party services

## Detail Design
- reliability, persist notification log and retry
- notification template/settings, {Hi - Subject}, OptIn
- rate limiting
- retry mechanism, add back to message queue
- security with token
- monitor message queue, increase worker if traffic too high
- analysis, open/click/engament rate

## Update Design
```java
-                     +---> Analysis Service <------------------------------+
-                     |                           +-<--- retry on error ----+
- ServiceN --> NotifSystem(Auth,RateLimit) ---> iOS PN ------------------> Worker ----> APNS ----> iOS
-                     |                                                     +---> Notification Template
-                   Cache                                                   +---> Notification Log
-                     |
-                DB(device/user/setting info) 
```

## Terminology
- breaking news
- ambiguous
- opt-out
- get buy-in
- propagate push notification
- decouple the system components
- nature
- dedupe
- fine-grained control
- overwhelmed
- opt-in
- decouple
- indispensable
- dug deep
- opt-out
