package algo.leetcode.LC0785.Bipartite_BFS_Coloring;

import java.util.*;

class Solution {

    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] visited = new int[n];
        for (int i = 0, a = 0, c = 0; i < n; i ++) {
            if (visited[i] == 0) {
                Queue<Integer> bfs = new ArrayDeque<>();
                for (bfs.add(i), c = 1; !bfs.isEmpty(); c = 3 - c) {
                    for (int m = bfs.size(); m > 0; m --) {
                        visited[a = bfs.poll()] = c;
                        for (int b : graph[a]) {
                            if (visited[b] == c) {
                                return false;
                            } else if (visited[b] == 0) {
                                bfs.add(b);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
