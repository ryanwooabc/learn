package algorithms.leetcode.LC2328.Flatten_Sorting_DP;

import java.util.*;

// 2328-count-increasing-path
// Q: https://leetcode.com/problems/number-of-increasing-paths-in-a-grid/

class Solution {

    // 2328 - Sort + DP
    // Time: O(M * N * LogM * LogN)
    // Space: O(M * N)
    // Rank: 50%
    /* Steps:           grid = [[1,1],[3,4]]
                        vals = [[0, 0, 1], [0, 1, 1], [1, 0, 3], [1, 1, 4]]
                        vals[0], dp[0][0] = 1, ans = 1, dp[0][1] = 1, dp[1][0] = 1
                        vals[1], dp[0][1] = 1, ans = 2, dp[1][1] = 1
                        vals[2], dp[1][0] = 2, ans = 4, dp[1][1] = 2
                        vals[3], dp[1][1] = 3, ans = 7

                        1 1
                        2 3
    */
    public int countPaths(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] vals = new int[m * n][];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                vals[i * n + j] = new int[]{i, j, grid[i][j]};
            }
        }

        int[] D = {0, 1, 0, -1, 0};
        long[][] dp = new long[m][n];
        Arrays.sort(vals, (a, b) -> a[2] - b[2]);

        long ans = 0, M = 1_000_000_007;
        for (int[] e : vals) {
            int i = e[0], j = e[1];
            ans = (ans + ++dp[i][j]) % M;
            for (int k = 0; k < 4; k++) {
                int x = i + D[k], y = j + D[k + 1];
                if (x >= 0 && x < m && y >= 0 && y < n && grid[i][j] < grid[x][y]) {
                    dp[x][y] = (dp[x][y] + dp[i][j]) % M;
                }
            }
        }

        return (int) ans;
    }
}