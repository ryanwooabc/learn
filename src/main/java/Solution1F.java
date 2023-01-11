class Solution1F {

    /* Steps:        012345678901
                s = "aabaaaacaabc", k = 2
                                 |
                     aabaaaacaabcaabaaaacaabc
                                            j
                                i
    */
    public int takeCharacters(String s, int k) {
        int[] count = new int[128];
        int n = s.length(), ans = n + 1, a = 0, b = 0, c = 0, j = 0;
        for (j = 0; j < n; j ++) {
            count[s.charAt(j)] ++;
            if (count['a'] == k && count['b'] == k && count['c'] == k) {
                ans = j + 1;
                break;
            }
        }
        j --;
        for (int i = n - 1; i >= 0; ) {
            System.out.println(i + " " + j);
            if (j >= 0 && count[s.charAt(j)] > k) {
                j --;
            } else if (j >= 0 && s.charAt(i) == s.charAt(j)) {
                i --;
                j --;
            }
            ans = Math.min(ans, j - i + 1);
        }
        return ans > n ? -1 : ans;
    }

    public static void main(String[] args) {
        System.out.println(new Solution1F().takeCharacters("aabaaaacaabc", 2));
    }
}
/*
"aabaaaacaabc"
2
"a"
1
*/