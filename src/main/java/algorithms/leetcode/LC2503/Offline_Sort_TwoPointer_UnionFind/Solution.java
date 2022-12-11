package algorithms.leetcode.LC2503.Offline_Sort_TwoPointer_UnionFind;

import java.util.*;

public class Solution {

    // Offline + UnionFind
    public int[] maxPoints11(int[][] grid, int[] queries) {
        int m = grid.length, n = grid[0].length, t = m * n, k = queries.length;
        int[][] nums = new int[t][], q = new int[k][];
        for (int i = 0; i < m; i ++) {
            for (int j = 0; j < n; j ++) {
                nums[i * n + j] = new int[] { grid[i][j], i, j };
            }
        }
        Arrays.sort(nums, (a, b) -> a[0] - b[0]);

        for (int i = 0; i < k; i ++) {
            q[i] = new int[] { queries[i], i };
        }
        Arrays.sort(q, (a, b) -> a[0] - b[0]);

        int[] ans = new int[k], root = new int[t], count = new int[t], D = { 0, -1, 0, 1, 0 };
        for (int i = 0; i < t; i ++) {
            root[i] = i;
            count[i] = 1;
        }

        for (int i = 0, j = 0; i < k; i ++) {
            for ( ; j < t && nums[j][0] < q[i][0]; j ++) {
                int x = nums[j][1], y = nums[j][2];
                for (int d = 0; d < 4; d ++) {
                    int nx = x + D[d], ny = y + D[d + 1];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] < q[i][0]) {
                        int r = find(root, x * n + y), nr = find(root, nx * n + ny);
                        if (r != nr) {
                            root[r] = nr;
                            count[nr] += count[r];
                        }
                    }
                }
            }
            ans[q[i][1]] = grid[0][0] < q[i][0] ? count[find(root, 0)] : 0;
        }
        return ans;
    }

    int find(int[] root, int i) {
        return root[i] == i ? i : (root[i] = find(root, root[i]));
    }
}
