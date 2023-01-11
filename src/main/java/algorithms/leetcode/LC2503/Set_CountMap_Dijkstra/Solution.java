package algorithms.leetcode.LC2503.Set_CountMap_Dijkstra;

import java.util.*;

public class Solution {

    // PQ
    public int[] maxPoints(int[][] grid, int[] queries) {
        Set<Integer> visited = new HashSet<>();
        TreeMap<Integer, Integer> count = new TreeMap<>();
        for (int v : queries) {
            count.put(v, -1);
        }

        int m = grid.length, n = grid[0].length, k = queries.length;
        int[] ans = new int[k], D = { 0, -1, 0, 1, 0 };
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.add(new int[] { grid[0][0], 0, 0});
        for (int q : count.keySet()) {
            while (!pq.isEmpty() && pq.peek()[0] < q) {
                int[] top = pq.poll();
                int v = top[0], x = top[1], y = top[2];
                if (visited.add(x * n + y)) {
                    for (int i = 0; i < 4; i ++) {
                        int nx = x + D[i], ny = y + D[i + 1];
                        if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                            pq.add( new int[] { grid[nx][ny], nx, ny });
                        }
                    }
                }
            }
            count.put(q, visited.size());
        }

        for (int i = 0, j = 0; i < k; i ++) {
            ans[i] = count.get(queries[i]);
        }
        return ans;
    }
}
