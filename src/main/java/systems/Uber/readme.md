# 0000-system-uber

## Functional Requirement
- drivers send their location and availability to the system every 3 seconds
- passengers can get nearby available drivers from the system
- passengers can request a ride, and the system will notify nearby drivers
- a driver will accept the ride, and both will see each other's location until trip done
- the driver will complite the ride and become available once the trip done
- QuadTree (Dynamic Grid) with frequent updates

## Non-functional Requirement
- high reliability/availability/scalability
- low latency
- QuadTree can help to find nearby driver quickly but fast update is not guaranteed

## Capacity Estimatioin
- 300M passengers, 1M drivers, 1M rides / day
- DAU: 1M passengers, 500K drivers
- Inbound: (ID 4 bytes + Lat 8 + Lon 8) * 500K = 10MBps


## System API

## Database Design
- passengers(id, lastName, firstName, gender, birthDate, phone, email)
- drivers(id, lastName, firstName, gender, birthDate, maker, model, color, plate, phone, email)

## High-level Design
```java
- Passenger ---> LB ---> Aggragator
-            \               |
-             PN         QuadTreeServer  <--- QuadTreeIndex ---> LocationDB
-           /      \         |
- Driver -----> LB --> LocationServer ---> SSD
```

## Detail Design

### QuadTree Update
- Keep<DriverID, [LastLocation, LatestLocation]> in memory
- reflect driver's current location every 15 seconds
- Memory: (4 bytes + lastLat 8 + lastLon + newLat + newLon) * 1M = 36 MB
- replicates

### Driver's Location
- Push: dedicated notification service, Long Polling or WebSocket
- pub/sub model, subscribe when opening app
- broadcast when DriverLocHashTable changed
- one driver has 5 subscribers
- Memory: DriverID 4 bytes * 500K + PassengerID 8 bytes * 500K * 3 = 21 MB
- Outbound: (DriverID 4 bytes + Lat 8 + Lon 8) * 500K * 5 = 50 MBps
- TODO: Pull when adding new drivers for existing subscribers

### Request Ride
- a passenger sends the request
- a aggragator requests nearby drivers from QuadTree servers
- the aggregator collect all of the result and sort by the rating
- the aggregator will send ride request to top 3 drivers
- one drivers will accept the request and send acknowledgement
- the aggregator will send cancelation to other drivers
- the aggregator will notify the passenger

## Availability
- primary-second: location server, notification server

## Ranking
- drivers(id, ..., trips, total)

## Enhancement
- client is slow or disconnected
- disconnected during ride
- billing handling
- pull vs push

## Terminology
- passenger
- regularly
- propagate,????????????????????????
- cushion