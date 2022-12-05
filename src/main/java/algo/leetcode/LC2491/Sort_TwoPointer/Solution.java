package algo.leetcode.LC2491.Sort_TwoPointer;

import java.util.Arrays;

// 2491-divide-players-into-teams-of-equal-skill
// Q: https://leetcode.com/problems/divide-players-into-teams-of-equal-skill/

class Solution {

    public long dividePlayers(int[] skills) {
        Arrays.sort(skills);
        int n = skills.length;
        long ans = 0, sum = skills[0] + skills[n - 1];
        for (int i = 0; i < n / 2; i++) {
            if (skills[i] + skills[n - 1 - i] != sum) {
                return -1;
            }
            ans += (skills[i] * skills[n - 1 - i]);
        }
        return ans;
    }
}
/*
[3,2,5,1,3,4]
[3,4]
[1,1,2,3]

java.lang.ArrayIndexOutOfBoundsException: Index 1999 out of bounds for length 1001
  at line 29, Solution.dividePlayers
  at line 54, __DriverSolution__.__helper__
  at line 84, __Driver__.main
*/
