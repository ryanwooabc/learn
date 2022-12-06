package algo.leetcode.LC1442.PrefixSum_SubarraySum_CountMap_CumuSum;

import java.util.*;

// 1442-count-xor-triplets
// Q: https://leetcode.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor/

class Solution {

    // 1442 - PrefixSum + SubarraySum + CountMap + CumuSum
    /* Steps:
                    i   j-1 j+1   k
                    a       b
                    a = b
                    a ^ b = 0

                    i   j   k   n
                      j-i-1
                          k-j-1
                          k-i-1
                                n - i - 1
                                n - j - 1
                                n - k - 1

                        ans[j] = j * 1 - (i + 1)
                        ans[k] = k * 2 - (i + 1) + (j + 1)

                    arr = [2,3,1,6,7]
                    i = 0, v = 010, p = 010
                    i = 1, v = 011, p = 001
                    i = 2, v = 001, p = 000, [2, 3, 1], i = 0, j = 1/2, k = 2, m = k - -1 - 1 = 2
                    i = 3, v = 110, p = 110
                    i = 4, v = 111, p = 001
    */
    public int countTriplets(int[] arr) {
        int n = arr.length, ans = 0;
        Map<Integer, Integer> count = new HashMap<>() {{ put(0, 1); }}, sum = new HashMap<>();
        for (int i = 0, prefix = 0; i < n; i ++) {
            prefix ^= arr[i];
            int c = count.getOrDefault(prefix, 0);
            int s = sum.getOrDefault(prefix, 0);
            ans += i * c - s;
            count.put(prefix, c + 1);
            sum.put(prefix, s + i + 1);
        }
        return ans;
    }

    public static void main(String[] args) {

    }
}