package algorithms.leetcode.LC1888.SlidingWindow;

class Solution {
    public int minFlips(String s) {
        int n = s.length(), a = 0, b = 0, ans = Integer.MAX_VALUE;
        for (int i = 0; i < n * 2; i ++) {
            a = s.charAt(i % n) == '0' + i % 2 ? a + 1 : a;
            b = s.charAt(i % n) == '0' + 1 - i % 2 ? b + 1 : b;
            if (i >= n - 1) {
                if ( i >= n) {
                    a = s.charAt(i - n) == '0' + (i - n) % 2 ? a - 1 : a;
                    b = s.charAt(i - n) == '0' + 1 - (i - n) % 2 ? b - 1 : b;
                }
                ans = Math.min(ans, Math.min(a, b));
            }
        }
        return ans;
    }
}