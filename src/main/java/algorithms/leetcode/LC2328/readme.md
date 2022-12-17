# Questions:
- 给定二维矩阵，找出严格递增路径的个数

# Input:
- 二维矩阵 grid

# Output:
- 严格递增路径的个数

# Constraints:
- m == grid.length
- n == grid[i].length
- 1 <= m, n <= 1000
- 1 <= m * n <= 10^5
- 1 <= grid[i][j] <= 10^5

# Test Case:
- grid = [[1,1],[3,4]], return 8
- grid = [[1],[2]], return 3

# Solution1: Flatten + Sorting + DP
- 将矩阵扁平化 [i, j, v], 并按值排序
- 创建动态规划数组 dp[m][n]
- 遍历每个 [i, j, v]
  - dp[i][j]增一，并累加到结果
  - 遍历上下左右四个邻接点 [x, y], 将dp[i][j]累加到dp[x][y]
- Time: O(MN*LogMN)
- Space: O(M*N)
