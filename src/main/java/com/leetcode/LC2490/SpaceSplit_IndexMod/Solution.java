package com.leetcode.LC2490.SpaceSplit_IndexMod;

// 2490-circular-sentence
// Q: https://leetcode.com/problems/circular-sentence/

class Solution {

    public boolean isCircularSentence(String sentence) {
        String[] words = sentence.split(" ");
        int n = words.length;
        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;
            String s = words[i], t = words[j];
            int a = s.length(), b = t.length();
            if (s.charAt(a - 1) != t.charAt(0)) {
                return false;
            }
        }
        return true;
    }
}
