# Questions:
- 给定一个矩阵，开始位置在左上角
- 给定一组查询，对于每个查询，可以在小于查询的邻近格子移动
- 每访问一个新格子加一分，求每个查询的分数

# Input:
- 二维数组 grid 和一维数组 queries

# Output:
- 包含每个查询得到分数的一维数组

# Constraints:
- m == grid.length
- n == grid[i].length
- 2 <= m, n <= 1000
- 4 <= m * n <= 10^5
- k == queries.length
- 1 <= k <= 104
- 1 <= grid[i][j], queries[i] <= 106

# Test Case:
- grid = [[1,2,3],[2,5,7],[3,5,1]], queries = [5,6,2], return [5, 8, 1]
- grid = [[5,2,1],[1,1,2]], queries = [3], return [0]

# Solution1: Offline + Sort + TwoPointer + UnionFind
- 将矩阵转成 [v, i, j] 的二维数组nums，按v排序
- 将查询转成 [v, i]的二维数组q，按q排序
- 初始化并查集的根数组和连通块大小数组
- 遍历q中所有元素
  - 遍历nums小于当前查询的所有元素
    - 遍历当前单元格周围的元素
      - 如果相邻元素在界内，则和当前元素归并，并更新当前连通块大小
  - 如果当前单元格小于查询元素，结果为0，否则为开始位置所在连通块的大小
- Time: O(MN * LogMN + Q * LogQ)
- Space: O(MN + Q)

# Solution2: Set + CountMap + PQ + BFS
- 将查询数组去重放入 count
- 新建优先队列 [v, i, j]，按v排列，并放入开始位置
- 遍历 count 里所有的键 q
  - 遍历优先队列里所有小于查询的单元格
    - 如果该单元格未访问，则加入更新分数
    - 遍历相邻元素，如果在界内则加入到优先队列中
  - 更新当前查询元素的分数
- 遍历查询数组，到 count 里查询分数
- Time: O(MN * LogMN * Q)
- Space: O(MN +Q)