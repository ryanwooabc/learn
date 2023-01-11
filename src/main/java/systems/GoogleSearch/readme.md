# Design Google Search
- https://www.educative.io/courses/grokking-modern-system-design-interview-for-engineers-managers/mE3VlYv9Omp
- https://www.google.com/search/howsearchworks/
- https://instagram-engineering.com/search-architecture-eeb34a936d3a
- https://medium.com/pinterest-engineering/manas-realtime-enabling-changes-to-be-searchable-in-a-blink-of-an-eye-36acc3506843
- https://engineering.fb.com/2013/03/14/core-data/under-the-hood-indexing-and-ranking-in-graph-search/
- https://www.youtube.com/watch?v=AguWva8P_DI

## Functional Requirement
- input tag
- search tag
- result twitter list
- optional: non-English, crawler, ranking

## Capacity Estimate
- 500M sites * 100 pages * 100KB = 5PB
- Index: 10PB
- 1B DAU * 10 searches / 24 / 3600 ~= 100K QPS

## High Level Design
- PageStore ---> Indexer ---> IndexStore
- Client ---> QueryService ---->--+

### Indexer
- Tokenization
- Normalization
- Stemming
- 整体索引

### Indexing Partition/Sharding
- By Document
    - Pros: PostingList smaller, local intersection, easy scale? update dynamically?
    - Cons:
- By Term
    - Pros: only subset of machines for a query
    - Cons: PostingList may not in memory

### PostingList Ranking

### Data Structure of Indexing in Memory
- Singly Linked List
- Variable-length Array
- Linked List of Fixed-size Array

### Data Structure of Indexing on Disk
- Doc Gap
- Variable Byte Encoding

### Update Index on Disk
- Rebuild
- Intermittent Merge
- Incremental Update
- 索引的文件数有没有讲究，要不要用一个进程合并小文件？

### 单一关键字搜索
- From LRU
- 硬盘索引，合并前后数据分别长什么样的啊
- Refer BigTable

- PostingList: <Keyword, [<Doc1, [P1, P2]>, <Doc2, [P3]>]


## Question
- Twitter Search索引所有的非助词？HashTag只索引Tag？
- 10M words -> [DocumentID1, DocumentID2] 倒排索引应该没多少数据吧
- PostingList那一页，每个网站后面的0,1,2,3,4,5,6是什么意思啊
- 需要在网页中的位置做什么啊，句子索引
- 偏移（Offset），记录单词在文档的开始和结束位置，用于高亮显示
- MapReduce是不是现在没什么人用了，面试官会不会问Spark啊
- Shuffling和Reducing是不是一对一啊，为啥要分成两步啊
- 要等所有的Map jobs结束后，才能开始Reduce jobs么
- Mapper 会故意做点什么，检测异常？

## Terminology
capitalization
synonyms

