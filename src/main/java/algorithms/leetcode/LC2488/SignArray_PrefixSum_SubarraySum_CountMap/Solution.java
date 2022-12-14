package algorithms.leetcode.LC2488.SignArray_PrefixSum_SubarraySum_CountMap;

import java.util.*;

// 2488-count-subarrays-with-median-k
// Q: https://leetcode.com/problems/count-subarrays-with-median-k/

class Solution {

    // 2488: SignArray + PrefixSum + SubarraySum + CountMap
    // Time: O(N)
    // Space: O(N
    // Rank: 68.17%
    /* Steps:  nums = [3,2,1,4,5], k = 4
                     -1 -1 -1 0 1
                   0 -1 -2 -3 -3 -2
                           [     ] = 0
                             [ ] = 0
                             [   ] = 1

                    count = { 0: 1 }
                   i = 0, p = -1, count = { -1: 1, 0: 1 }
                   i = 1, p = -2, count = { -2: 1, -1: 1, 0: 1 }
                   i = 2, p = -3, count = { -3: 1, -2: 1, -1: 1, 0: 1 }
                                   rsum = 0, rsum = 1
                                   prefix[x] + rsum = prefix[index]
                                               0
                                               1

                                               count.get(prefix[index] - 0)
                                               count.get(prefix[index] - 1)

                   i = 3, p = -3, v = k, ans = count[-3] + count[-4]
                   i = 4, p = -2


              sum 0
                 0  1  2  3  4
                [1, 2, 3, 4, 5], n queries of range[i, j] sum, O(N^2)
    prefix = [0, 1, 3, 6, 10, 15],
                |-|
                |----|
                |---------------|
                                    r = [1, 4], sum[1, 4] = 2 + 3 + 4 + 5 (x)
                                                sum[1, 4] = sum[0, 4] - sum[0, 0] = prefix[4 + 1] - prefix[1] = 14

    */
    public int countSubarrays11(int[] nums, int k) {
        int n = nums.length, prefix = 0, i = 0, ans = 0;
        Map<Integer, Integer> count = new HashMap<>() {{ put(0, 1); }};
        for ( ; nums[i] != k; i ++) {
            prefix += nums[i] < k ? -1 : 1;
            count.put(prefix, count.getOrDefault(prefix, 0) + 1);
        }
        for (i ++, ans = count.get(prefix) + count.getOrDefault(prefix - 1, 0); i < n; i ++) {
            prefix += nums[i] < k ? -1 : 1;
            ans += count.getOrDefault(prefix, 0) + count.getOrDefault(prefix - 1, 0);
        }
        return ans;
    }

    public int countSubarrays(int[] nums, int k) {
        int n = nums.length, prefix = 0, i = 0, ans = 0;
        int[] count = new int[n + n + 1];
        for (count[0 + n] = 1; nums[i] != k; i ++) {
            prefix += nums[i] < k ? -1 : 1;
            count[prefix + n] ++;
        }

        for (i ++, ans = count[prefix + n] + count[prefix -1 + n]; i < n; i ++) {
            prefix += nums[i] < k ? -1 : 1;
            ans += count[prefix + n] + count[prefix -1 + n];
        }
        return ans;
    }
}