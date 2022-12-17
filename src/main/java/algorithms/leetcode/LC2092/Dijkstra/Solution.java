package algorithms.leetcode.LC2092.Dijkstra;

import java.util.*;

class Solution {
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        List<int[]>[] graph = new List[n];
        for (int i = 0; i < n; i ++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] m : meetings) {
            int a = m[0], b = m[1], t= m[2];
            graph[a].add(new int[] { b, t });
            graph[b].add(new int[] { a, t });
        }

        List<Integer> ans = new ArrayList<>();
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.add(new int[] { 0, 0 });
        pq.add(new int[] { firstPerson, 0 });
        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int i = top[0];
            if (null != graph[i]) {
                ans.add(i);
                for (int[] next : graph[i]) {
                    if (next[1] >= top[1]) {
                        pq.add(next);
                    }
                }
                graph[i] = null;
            }
        }
        return ans;
    }
}