package com.leetcode.LC2492.BuildGraph_BFS;

import java.util.*;

// 2492-minimum-score-of-a-path-between-two-cities
// Q: https://leetcode.com/problems/minimum-score-of-a-path-between-two-cities

class Solution {
    
    public int minScore(int n, int[][] roads) {
        Map<Integer, Integer>[] graph = new Map[n];
        for (int i = 0; i < n; i ++) {
            graph[i] = new HashMap<>();
        }
        for (int[] r : roads) {
            int a = r[0] - 1, b = r[1] - 1, c = r[2];
            graph[a].put(b, c);
            graph[b].put(a, c);
        }
        
        int ans = Integer.MAX_VALUE;
        boolean[] visited = new boolean[n];
        Queue<Integer> bfs = new ArrayDeque<Integer>() {{ add(0); }};
        for (visited[0] = true; !bfs.isEmpty(); ) {
            int i = bfs.poll();
            for (int j : graph[i].keySet()) {
                if (!visited[j]) {
                    bfs.add(j);
                    visited[j] = true;
                }
                ans = Math.min(ans, graph[i].get(j));
            }
        }
        return ans;
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