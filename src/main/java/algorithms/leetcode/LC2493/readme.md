# Questions: 
 - 把图中相邻接点放入相邻组，求最多的组数

# Input: 
 - 定点数 n，和边数组 edges

# Output:
 - 最多的组数

# Constraints:
 - 1 <= n <= 500
 - 1 <= edges.length <= 104
 - edges[i].length == 2
 - 1 <= ai, bi <= n
 - ai != bi
 - There is at most one edge between any pair of vertices.

# Test Case:
 - n = 6, edges = [[1,2],[1,4],[1,5],[2,6],[2,3],[4,6]], return 4
 - n = 3, edges = [[1,2],[2,3],[3,1]], return -1

# Solution1: UnionFind + BFS + Bipartite 
 - 创建并初始化 root
 - 遍历所有边，转成无向图，并归并
 - 遍历所有顶点，找出对应 root，从i出发找出图的深度
    - 遍历每层节点，创建下一层列表
    - 如果当前节点的邻居，如果在当前列表，则返回-1
    - 否则加入bfs和下一层列表
 - 如果深度为 -1，主程序返回 -1，否则更新当前根的最大深度
 - 遍历所有根，计算总组数
 - Time: O(V * E)
 - Space: O(V + E)
 
# Solution1F: BFS + Bipartite 
 - 遍历所有边，转成无向图
 - 遍历所有顶点i，从i出发找出图的深度
    - 创建层次数组，和BFS队列，初始化level[i]=1
    - 遍历队列的所有元素，每次d加一
        - 更新连通图里最小顶点r
        - 对每个顶点的邻居，如果level没有赋值，则为d+1
        - 如果level为d，则不能为二分图，返回-1
    - 连通图里最小顶点的为d-1
 - 遍历所有根，计算总组数
 - Time: O(V * E)
 - Space: O(V + E)