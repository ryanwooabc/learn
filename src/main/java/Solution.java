class Solution {

    /* Steps:        012345678901
                s = "aabaaaacaabc", k = 2
                                 |
                     aabaaaacaabcaabaaaacaabc
                                j
                     i
    */
    public int takeCharacters(String s, int k) {
        if (k == 0) {
            return 0;
        }
        int[] count = new int[128];
        int n = s.length(), ans = n + 1, a = 0, b = 0, c = 0, i = 0, j = 0;
        for (j = 0; j < n; j ++) {
            count[s.charAt(j)] ++;
            if (count['a'] >= k && count['b'] >= k && count['c'] >= k) {
                ans = ++ j;
                break;
            }
        }
        for ( ; j < n; j ++) {
            count[s.charAt(j)] ++;
        }
        while (i < n && count[s.charAt(i)] > k) {
            count[s.charAt(i ++)] --;
        }
        if (i < n && j >= n && count['a'] >= k && count['b'] >= k && count['c'] >= k) {
            ans = Math.min(ans, j - i);
        }
        for (; j < n + n; j ++) {
            count[s.charAt(j % n)] ++;
            while (i < n && count[s.charAt(i)] > k) {
                count[s.charAt(i ++)] --;
            }
            if (i < n && j >= n && count['a'] >= k && count['b'] >= k && count['c'] >= k) {
                ans = Math.min(ans, j - i + 1);
            }
        }
        return ans > n ? -1 : ans;
    }

    public static void main(String[] args) {
//        System.out.println(new Solution().takeCharacters("aabaaaacaabc", 2));
//        System.out.println(new Solution().takeCharacters("a", 0));
//        System.out.println(new Solution().takeCharacters("aacc", 2));
        System.out.println(new Solution().takeCharacters("cbbac", 1));
    }
}
/*
"aabaaaacaabc"
2
"a"
1
*/