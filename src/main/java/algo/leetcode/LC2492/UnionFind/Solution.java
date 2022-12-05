package algo.leetcode.LC2492.UnionFind;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

// 2492-minimum-score-of-a-path-between-two-cities
// Q: https://leetcode.com/problems/minimum-score-of-a-path-between-two-cities

class Solution {
    
    public int minScore(int n, int[][] roads) {
        int[] root = new int[n];
        for (int i = 0; i < n; i ++) {
            root[i] = i;
        }
        for (int[] r : roads) {
            int a = find(root, r[0] - 1), b = find(root, r[1] - 1);
            if (a != b) {
                root[a] = root[b];
            }
        }        
        int t = find(root, 0), ans = Integer.MAX_VALUE;
        for (int[] r : roads) {
            if (find(root, r[0] - 1) == t) {
                ans = Math.min(ans, r[2]);
            }
        }
        return ans;
    }
    
    int find(int[] root, int i) {
        return root[i] == i ? i : (root[i] = find(root, root[i]));
    }
    
    public int minScore1F(int n, int[][] roads) {
        int m = roads.length;
        int min = roads[0][2];
        for(int i=0;i<m;i++){
            min = Math.min(min, roads[i][2]);
        }
        return min;
    }    
}