package algorithms.leetcode.LC1524.PrefixSum_MOD_SubarraySum_CountArray;

// 1524-number-of-sub-arrays-with-odd-sum
// Q: https://leetcode.com/problems/number-of-sub-arrays-with-odd-sum/

class Solution {
    public int numOfSubarrays(int[] nums) {
        long ans = 0;
        int[] count = { 1, 0 };
        for (int i = 0, p = 0; i < nums.length; i ++) {
            p = (p + nums[i]) % 2;
            ans = (ans + count[1 - p]) % 1_000_000_007;
            count[p] ++;
        }
        return (int)ans;
    }
}
