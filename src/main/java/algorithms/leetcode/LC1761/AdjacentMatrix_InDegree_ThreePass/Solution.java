package algorithms.leetcode.LC1761.AdjacentMatrix_InDegree_ThreePass;

class Solution {
    public int minTrioDegree(int n, int[][] edges) {
        int[] degree = new int[n];
        boolean[][] graph = new boolean[n][n];
        for (int[] e : edges) {
            int a = e[0] - 1, b = e[1] - 1;
            graph[a][b] = graph[b][a] = true;
            degree[a] ++;
            degree[b] ++;
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i ++) {
            for (int j = i + 1; j < n; j ++) {
                if (graph[i][j]) {
                    for (int k = j + 1; k < n; k ++) {
                        if (graph[i][k] && graph[j][k]) {
                            ans = Math.min(ans, degree[i] + degree[j] + degree[k] - 6);
                        }
                    }
                }
            }
        }
        return Integer.MAX_VALUE == ans ? -1 : ans;
    }
}