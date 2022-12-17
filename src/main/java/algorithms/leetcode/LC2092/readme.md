# Questions:
- 给定会议数组，pi和pj会在t开会
- p0有个秘密，p0和f会在t0开会
- 问开完所有会议，所有知道秘密的人

# Input:
- 人数 n
- 会议数组 meetings = [[pi, pj, t]]
- 第一个人 f

# Output:
- 所有知道秘密的人的列表

# Constraints:
- 2 <= n <= 10^5
- 1 <= meetings.length <= 10^5
- meetings[i].length == 3
- 0 <= xi, yi <= n - 1
- xi != yi
- 1 <= timei <= 10^5
- 1 <= firstPerson <= n - 1

# Test Case:
- n = 6, meetings = [[1,2,5],[2,3,8],[1,5,10]], f = 1, return [0,1,2,3,5]
- n = 4, meetings = [[3,1,3],[1,2,2],[0,3,3]], f = 3, return [0,1,3]
- n = 5, meetings = [[3,4,2],[1,2,1],[2,3,1]], f = 1, return [0,1,2,3,4]

# Solution1: Dijkstra
- 将会议数组转成图 List<int[]> graph[i].add([j, t])
- 构建优先队列 pq [i, t]， 按t排序，并放入 [0, 0]和[f, 0]
- 如果pq不为空，则弹出时间最小的人员 [i, ti]
  - 如果 i 未访问, 即graph[i] 不为空，将i放入结果
  - 遍历i的所有邻接点 [j, tj], 如果tj >= ti,将[j, tj]放入pq
  - 将graph[i] 置空
- Time: O(V * E)
- Space: O(V + E)
