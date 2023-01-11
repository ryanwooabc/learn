package algorithms.leetcode.LC2506.BitMask_CountMap_Multiplication;

import java.util.*;

// 2506-count-pairs-of-similar-strings
// Q: https://leetcode.com/problems/count-pairs-of-similar-strings/

class Solution {

    public int similarPairs(String[] words) {
        Map<Integer, Integer> count = new HashMap<>();
        for (String s : words) {
            int mask = 0;
            for (int c : s.toCharArray()) {
                mask |= 1 << (c - 'a');
            }
            count.put(mask, count.getOrDefault(mask, 0) + 1);
        }
        int ans = 0;
        for (int c : count.values()) {
            ans += c * (c - 1) / 2;
        }
        return ans;
    }

    public int similarPairs11(String[] words) {
        int n = words.length, ans = 0;
        for (int i = 0; i < n; i++) {
            int a = 0;
            for (char c : words[i].toCharArray()) {
                a |= 1 << (c - 'a');
            }
            for (int j = i + 1; j < n; j++) {
                int b = 0;
                for (char c : words[j].toCharArray()) {
                    b |= 1 << (c - 'a');
                }
                if (a == b) {
                    ans++;
                }
            }
        }
        return ans;
    }
}
