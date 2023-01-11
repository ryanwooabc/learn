package algorithms.leetcode.LC2503.Offline_Sort_TwoPointer_UnionFind;

import java.util.Arrays;
import algorithms.template.graph.UnionFindCount;

public class Solution2 {

    // Offline + UnionFind
    public int[] maxPoints(int[][] grid, int[] queries) {
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

        UnionFindCount uf = new UnionFindCount(t);
        int[] ans = new int[k], D = { 0, -1, 0, 1, 0 };
        for (int i = 0, j = 0; i < k; i ++) {
            for ( ; j < t && nums[j][0] < q[i][0]; j ++) {
                int x = nums[j][1], y = nums[j][2];
                for (int d = 0; d < 4; d ++) {
                    int nx = x + D[d], ny = y + D[d + 1];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] < q[i][0]) {
                        uf.union(x * n + y, nx * n + ny);
                    }
                }
            }
            ans[q[i][1]] = grid[0][0] < q[i][0] ? uf.count(0) : 0;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Solution2().maxPoints(new int[][] {{1, 2, 3}, {2, 5, 7}, {3, 5, 1}}, new int[] {5, 6, 2})));
        System.out.println(Arrays.toString(new Solution2().maxPoints(new int[][] {{5, 2, 1}, {1, 1, 2}}, new int[] {3})));
    }
}
