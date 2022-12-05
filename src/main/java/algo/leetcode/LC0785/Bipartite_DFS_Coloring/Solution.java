package algo.leetcode.LC0785.Bipartite_DFS_Coloring;

public class Solution {

    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        for (int i = 0; i < n; i ++) {
            if (color[i] == 0 && !valid(graph, color, i, 1)) {
                return false;
            }
        }
        return true;
    }

    boolean valid(int[][] graph, int[] color, int i, int c) {
        if (color[i] != 0) {
            return color[i] == c;
        }
        color[i] = c;
        for (int j : graph[i]) {
            if (!valid(graph, color, j, 3 - c)) {
                return false;
            }
        }
        return true;
    }
}
