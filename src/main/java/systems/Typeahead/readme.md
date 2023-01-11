# Design AutoComplete / TypeAhead
- auto complete, type ahead, search as you type, incremental search
- predict / suggest query

## Functional Requirements
- users type prefix
- top 10 search suggestions with the prefix are listed
- suggestions are sorted by popularity, historical query frequency
- update frequency table weekly or real-time
- case insensitive
- optional: spell check, auto correct, multi-language support

## Non-functional Requirements
- real time, suggestions will show in 200ms
- Scalable
- High available

## Capacity Estimation
- 10M DAU, 10 searches / day, 4 words, 5 characters
- QPS: 10M * 10 * 4 * 5 / 24 / 3600 = 2B / 10^5 = 20K, Peak 40K QPS
- 2B * 20% unique * 50% top = 200M unique tems to index
- Storage： 200M * 30 bytes = 6GB
- One year: 6GB * 2% increase * 365 days = 48GB
- Memory: 200M prefixes * top 10 * (20 + 8) bytes = 60GB

## System API
- int query(String word);
- List<String> suggest(String prefix);

## Database Design
- queries(id, word, timestamp)
- frequencies(id, query, frequency)

## High-level Design
- Data Gathering Service
    - Store query frequency, may offline or sampling
- Query Service
    - Option 1: select query from frequencies where query like 'prefix%' order by frequency desc limit 5;
    - Option 2: Trie (Retrieve)

## Detail Design
- Trie Data Structure
    - Empty root node
    - Find a prefix: O(P), P: length of a prefix, N: total number of nodes in a trie
    - Traverse substree: O(C), C: number of descendant of a given node
    - Sort descendant and get top k, O(C * LogK)
    - Type "tr", return { tree: 10, true:35, try: 29 }
    - Total time: O(P + C + C * LogK)
    - TODO: merge node if only one branch
- Optimized Trie
    - Limit the max length of a prefix, max 50, not index long sentence, O(1)
    - Cache top search queries in each node, trading space for time
    - Find the prefix node: O(1)
    - Return Top K, O(1)
- Data Gathering Service
    - Build Trie from analytics or logging
    - Option 1: update Trie in real-time (on every query), Twitter
    - Option 2: Schedule updating, Google

- AnalyticsLogs ---> Aggregator --> AggregatedData ---> Workers ---> WeeklyUpdate ---> TrieDB ---> TrieCache

- AnalyticsLogs: append only, <query, timestamp>
- Aggregator: aggregate in a shotr time for real-time application like Twitter, or weekly for others
- AggregatedData: <query, weekStartTime, frequency>
- Workers: asynchronous job at regular intervals
- TrieCache: distributed cache system, weekly snapshot of TrieDB
- TrieDB: persistent storage
    - DocumentDB: serialized
    - KV Store: <Prefix, TopQueries>, distributed KV Store

- App/Client ---> LB ---> QueryService ---> FilterLayer ---> TrieCache ---> TrieDB

- QueryService
    - light-weight AJAX
    - replenish data back to cache when offline or out of memory
    - cache in browser with TTL like Google, cache-control: private, max-age=3600
    - data sampling, log every N queries

- Trie Operations:
    - Create: by aggregated Analytics Logs
    - Update: weekly
    - Delete: hateful, violent, dangerous

# Sacalability / Sharding
	- Option 1: range based, on the first character, or multi-levels, but uneven distribution
	- Option 2: shard by frequency or server capacity
	- Option 3: hash prefix

## Fault Tolerance
- Update Secondary when using Primary

## Enhancement
- send request when stopping for 50ms
- start sending request after 3 character typed
- local browser cache
- push cache to CDN
- treading / real-time search, complicated, more weight for query, stream system
- TODO: personalization

## Terminology
- tackle a question
- predict
- artiticulate the query better
- lead them a help
- cause stuttering
- realistic
- is broken down into two
- on a daily basis, 以一天为周期
- underlying foundation
- asynchronous
- asynchronized
- replenish, 补充
- ancestor
- descendant
- glance
- mitigate the imbalance prolem
- ancient
- articulate
- appropriate
- in a sequential manner
- phrase

