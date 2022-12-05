package algo.leetcode.LC2491.CountArray_Multiply;

import java.util.*;

// 2491-divide-players-into-teams-of-equal-skill
// Q: https://leetcode.com/problems/divide-players-into-teams-of-equal-skill/

class Solution {

    public long dividePlayers(int[] skill) {
        int[] count = new int[2001];
        long n = skill.length, m = n / 2, sum = 0;
        for (int v : skill) {
            sum += v;
            count[v]++;
        }
        long p = sum / m, ans = 0;
        if (p * m != sum) {
            return -1;
        }
        for (int i = 1; i <= p / 2; i++) {
            int j = (int) (p - i);
            if (i == j) {
                if (count[i] % 2 == 1) {
                    return -1;
                }
                ans += 1L * i * j * count[i] / 2;
            } else {
                if (count[i] != count[j]) {
                    return -1;
                }
                ans += 1L * i * j * count[i];
            }
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
