package algorithms.leetcode.LC2448.BinarySearch_Median;

import java.util.*;

// 2448-minimum-cost-to-make-array-equal
// Q: https://leetcode.com/problems/minimum-cost-to-make-array-equal/

class Solution {

    // 2448 - BinarySearch + TwoMiddle
    // Time: O(N * LogN)
    // Space: O(1)
    // Rank: 85.41%
    // Question: 对一个有权重的数组，找出所有数变成一样的最小开销
    /* Hints:
        用二分找出带权重的中位数
        计算变成中位数和中位数加一的开销
        如果前者小，最佳值在前半段，移动右侧
        如果后者小，最佳值在后半段，移动左侧
    */
    /* Steps:   nums = [1, 2, 3, 5]
                           m1 m2
                           c1 c2
    */
    public long minCost(int[] nums, int[] cost) {
        long lo = 1, hi = 1_000_001, c1 = 0, c2 = 0;
        while (lo < hi) {
            long mid = (lo + hi) / 2;
            c1 = c2 = 0;
            for (int i = 0; i < nums.length; i ++) {
                c1 += Math.abs(nums[i] - mid) * cost[i];
                c2 += Math.abs(nums[i] - mid - 1) * cost[i];
            }
            if (c1 < c2) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return Math.min(c1, c2);
    }

    // Histgram
    // PrefixSum
    /* Steps:   nums = [1,3,5,2], cost = [2,3,1,14]


        cost: 2 14 3 1
        nums: 1  2 3 5
              v = 1, sum =       1 * 14 + 2 * 3 + 4 * 1 = 24
              v = 2, sum = 1 * 2        + 1 * 3 + 3 * 1 = 8

    */
    public long minCostPrefixSum(int[] nums, int[] cost) {
        int n = nums.length;
        int[][] vals = new int[n][];
        for (int i = 0; i < n; i ++) {
            vals[i] = new int[] { nums[i], cost[i]};
        }
        Arrays.sort(vals, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        long sum = 0;
        for (int[] v : vals) {
            sum += (v[0] - vals[0][0] - 0L) * v[1];
        }
        long ans = sum;
        long[] prefix = new long[n + 1], suffix = new long[n + 1];
        for (int i = 0, j = n; i < n; i ++, j --) {
            prefix[i + 1] = prefix[i] + vals[i][1];
            suffix[j - 1] = suffix[j] + vals[j - 1][1];
        }
        // System.out.println(Arrays.toString(prefix));
        // System.out.println(Arrays.toString(suffix));
        for (int i = 1; i < n; i ++) {
            long diff = vals[i][0] - vals[i-1][0];
            sum = sum + diff * prefix[i] - diff * suffix[i];
            ans = Math.min(ans, sum);
            // System.out.print(sum + " ");
        }
        return ans;
    }
}