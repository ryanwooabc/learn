# Questions: 
 - 求图中点1到点n的最小路值

# Input: 
 - 点数n和路的数组(两点加权重)

# Output:
 - 最小路值

# Constraints:
 - 2 <= n <= 105
 - 1 <= roads.length <= 105
 - roads[i].length == 3
 - 1 <= ai, bi <= n
 - ai != bi
 - 1 <= distancei <= 104
 - There are no repeated edges.
 - There is at least one path between 1 and n.

# Test Case:
 - n = 4, roads = [[1,2,9],[2,3,6],[2,4,5],[1,4,7]], return 5
 - n = 4, roads = [[1,2,2],[1,3,4],[3,4,7]], return 2

# Solution1: BuildGraph + BFS
 - 讲边转成带权重的无向图，创建访问标记
 - 创建队列，放入节点0
 - BFS访问所有点和边，更新最小边
 - Time: O(V + E)
 - Space: O(V + E)

# Solution2: UionFind
 - 创建并初始化root数组
 - 遍历所有边，合并每对顶点
 - 遍历所有边，如果和节点0同根，则更新最小的结果
 - Time: O(V + E)
 - Space: O(V)