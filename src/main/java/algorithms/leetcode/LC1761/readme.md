# Questions:
- 连通三件套指三个顶点两两之间有边
- 连通三件套的度为只有一个顶点在连通三件套里的边的个数
- 求一个无向图最小连通三件套的度
- 如果没有连通三件套返回 -1

# Input:
- 顶点个数 n
- 无向图边的数组 edges = [[ui, vi]]

# Output:
- 无向图最小连通三件套的度

# Constraints:
- 2 <= n <= 400
- edges[i].length == 2
- 1 <= edges.length <= n * (n-1) / 2
- 1 <= ui, vi <= n
- ui != vi
- There are no repeated edges.

# Test Case:
- n = 6, edges = [[1,2],[1,3],[3,2],[4,1],[5,2],[3,6]], return 3
- n = 7, edges = [[1,3],[4,1],[4,3],[2,5],[5,6],[6,7],[7,5],[2,6]], return 0

# Solution1: AdjacentMatrix + InDegree + ThreePass
- 将边的数组转换为邻接矩阵 adj 和入度数组 in
- 遍历一个顶点 i
  - 从 i+1 遍历另外一个顶点 j
    - 忽略不相邻的顶点对
    - 从 j+1 遍历第三个顶点 k
      - 如果三个顶点两两有边
      - 将 i/j/k 的入度和 - 6 更新到最小答案
- 如果答案仍为最大值则返回 -1
- Time: O(E + V^3)
- Space: O(V^2)