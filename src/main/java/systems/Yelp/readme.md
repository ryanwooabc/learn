# 0000-system-yelp

## Functional Requirement
- create/read/update/delete places
- add description and upload picture for a place
- find nearby places with longitude, latitude and radius
- query with restaraunt name, category, characters, cursine
- comment a place, including text, picture and rating

## Non-functional Requirement
- high reliability/availability/scalability
- low latency
- TODO: pagination token

## Capacity Estimatioin
- 500M places, 100K QPS
- 20% growth of the place count / year
- 100M MAU * 10 queries / month
- 500 pictures and 1000 comments / place
- Read: 100M * 10 / 30 / 24 / 3600 ~= 300 QPS
- Storage: 1M * (2MB * 500 + 1KB * 1000) = 1PB

## System API
- create(token, name, lat, lon, desc, category)
- search(token, query, lat, lon, radius, categories, sort, count, pageToken)
- token: rate limiter

## Database Design
- categories(id, name)
- places(id, name, lat, lon, desc, categoryID)
- (8 bytes + 256 + 8 + 8 + 512 + 4) = 796 bytes ~= 800 bytes
- reviews(id, placeId, content, rated, ratings)
- (8 bytes + 8 bytes + 512 + 4 + 4) ~= 600 bytes


## High-level Design

- Client ---> LB ---> AppSvr ---> AggregatorN ---> QuadTreeServer ---> QuadTreeIndexer -+-> DB
-                       +---------------------------------------------------------------+

-            +--> CDN
- Customer --+--> LB -->
- Business --+

## Detail Design
- location not changed often

### SQL solutioin
- index lat and lon
- intersect 2 huge lists
- Pros: Easy way
- Cons: slow to merge huge lists

### Fixed-size Grid
- Statically defined grids, GridID 4 bytes
- find the grid which area matches
- Store <GridID, Set<PlaceID>> in memory
- Earth radius is 4K miles, Surface ares = 4 * Pai * 16M ~= 200M Miles^2
- GridID 4 bytes * 200M + PlaceID 8 bytes * 500M ~= 4GB
- Pros: query fastly
- Cons: not uniformly distributed, hot grids

### Dynamic-size Grid, QuadTree
- not more than 500 place in a grid for fast query
- build: break down hot grid into four child grids
- find: move to the proper child util no children
- find neighbors: doubly linked list of leaf nodes, or pointer to parent
- Memory: (ID + Lat + Lon) * 500M = 12 GB
- Grids: 500M / 500 = 1M leaf nodes
- ParentNode: 1M / 3 * 4 children * 8 bytes = 10 MB
- TODO: Split GuadTree

## Sharding
- by region, cons: hot region
- by PlaceID, consitant hash, aggregate

## Replicates
- primary-secondary
- Keep <QuadTreeServerID, Set<Place>> in memory
- rebuild QuadTree servers

## Cache
- Database
- LRU

## Load Balancing
- between Client and AppSvr
- between AppSvr and Aggregator
- Round Robin to Intelligent

## Ranking
- by star, Top K


## Terminology
- proximity server
- nearby attraction
- offer different options
- archive
- massive
- as a contrast
- pythagorean theorem
- undoubtedly
- coastal lines
- thickly populated area
- off-the-shelf solution


